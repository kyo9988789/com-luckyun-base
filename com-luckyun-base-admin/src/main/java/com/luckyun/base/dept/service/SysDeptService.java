package com.luckyun.base.dept.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.luckyun.base.company.mapper.SysCompanyMapper;
import com.luckyun.base.dept.mapper.SysDeptMapper;
import com.luckyun.base.dept.param.SysDeptParam;
import com.luckyun.base.hepler.SysDatadicConstantNameAlias;
import com.luckyun.base.hepler.SysDatadicHelper;
import com.luckyun.core.annotation.GlobalLockByRedis;
import com.luckyun.core.annotation.TransactionalException;
import com.luckyun.core.data.BaseMapper;
import com.luckyun.core.data.BaseService;
import com.luckyun.core.data.drag.DragHelperService;
import com.luckyun.core.data.filter.AuthInfo;
import com.luckyun.core.exception.CustomException;
import com.luckyun.core.km.keyhelper.IDGenerate;
import com.luckyun.model.company.SysCompany;
import com.luckyun.model.dept.SysDept;

@Service
public class SysDeptService extends BaseService<SysDept> {
    
    @Autowired
    SysDeptMapper sysDeptMapper;
   
    @Autowired
    @Qualifier("dragHelperService")
    private DragHelperService dragHelperService;
    
    @Autowired
    private SysDatadicHelper sysDatadicHelper;
    
    @Autowired
    private SysCompanyMapper sysCompanyMapper;
    
    @Override
    public BaseMapper<SysDept> getMapper() {
        return sysDeptMapper;
    }
    
    public List<SysDept> findAll(SysDeptParam condition) {
        AuthInfo authInfo = this.getAuthInfo();
        condition.setIcompanyid(authInfo.getIcompanyid());
        List<SysDept> list = sysDeptMapper.findAll(condition);
        for(SysDept sysDept : list) {
        	convertDatadic(sysDept);
        }
        return list;
    }
    
    public List<SysDept> findAllByJurs(SysDeptParam extryParam){
    	AuthInfo authInfo = this.getAuthInfo();
    	if("administrator".equals(authInfo.getSloginid())) {
    		extryParam.setIcompanyid(authInfo.getIcompanyid());
            List<SysDept> list = sysDeptMapper.findAll(extryParam);
            for(SysDept sysDept : list) {
            	convertDatadic(sysDept);
            }
            return list;
    	}
    	List<SysDept> depts = sysDeptMapper.findDeptJuris(authInfo.getIndocno());
    	List<SysDept> allNotParentList = new ArrayList<SysDept>();
    	Set<Long> allJursDeptIdList = new HashSet<>();
    	//查找所属的子集部门;
    	for(SysDept dept : depts) {
    		allNotParentList.addAll(sysDeptMapper.findDeptsByIparentid(dept.getIndocno()));
    	}
    	//找到所有的节点,也是包含所有的结果
    	for(SysDept dept : allNotParentList) {
    		String[] sidccArr = dept.getSidcc().split("/");
    		for(String sid : sidccArr) {
    			if(!StringUtils.isEmpty(sid)) {
    				allJursDeptIdList.add(Long.valueOf(sid));
    			}
    		}
    	}
    	//对子集结果进行排序
    	Map<Long,SysDept> resultMap = 
    			allNotParentList.stream().filter(e -> allJursDeptIdList.contains(e.getIndocno()))
    			.sorted(new Comparator<SysDept>() {

					@Override
					public int compare(SysDept o1, SysDept o2) {
						return o2.getIsort() - o1.getIsort();
					}
				})
    			.collect(Collectors.toMap(SysDept::getIndocno, Function.identity(),(oldValue, newValue) -> oldValue));
    	List<SysDept> result = new ArrayList<SysDept>();
    	for(Map.Entry<Long, SysDept> entry : resultMap.entrySet()) {
    		result.add(entry.getValue());
    	}
    	List<Long> resultIndocno = result.stream().map(SysDept::getIndocno).collect(Collectors.toList());
    	List<Long> needParents = allJursDeptIdList.stream().filter(e -> !resultIndocno.contains(e)).collect(Collectors.toList());
    	if(needParents != null && needParents.size() >= 1) {
	    	SysDeptParam condition = new SysDeptParam();
	    	condition.setIndocnos(needParents);
	    	List<SysDept> parents = sysDeptMapper.findDeptByOtherCondition(condition);
	    	result.addAll(parents);
    	}
    	Map<Long,SysDept> lastResultMap = result.stream()
    			.collect(Collectors.toMap(SysDept::getIndocno, Function.identity(),(oldValue, newValue) -> oldValue));
    	SysCompany company = sysCompanyMapper.findOne(authInfo.getIcompanyid());
    	for(SysDept sysDept : result) {
    		String[] sidccArr = sysDept.getSidcc().split("/");
    		List<String> scc  =new ArrayList<>();
    		for(String sid : sidccArr) {
    			if(!StringUtils.isEmpty(sid)) {
    				SysDept dept = lastResultMap.get(Long.valueOf(sid));
    				if(dept != null) {
    					scc.add(dept.getSname());
    				}
    			}
    		}
    		sysDept.setScc(String.join("/", scc));
    		String companyNm = company != null ? company.getSname() : "";
    		sysDept.setCompanyname(companyNm);
    	}
    	return extryParamHandle(extryParam,result);
    }
    
    private List<SysDept> extryParamHandle(SysDeptParam extryParam ,List<SysDept> content){
    	List<SysDept> result = content;
    	if(extryParam.getIlevelstart() != null && extryParam.getIlevelend() != null) {
    		result = result.stream().filter(e -> e.getIlevel() >= extryParam.getIlevelstart() 
    				&& e.getIlevel() <= extryParam.getIlevelend()).collect(Collectors.toList());
    	}else if (extryParam.getIlevelstart() != null) {
    		result = result.stream().filter(e -> e.getIlevel() >= extryParam.getIlevelstart()).collect(Collectors.toList());
    	}else if (extryParam.getIlevelend() != null) {
    		result = result.stream().filter(e -> e.getIlevel() <= extryParam.getIlevelend()).collect(Collectors.toList());
    	}
    	return result;
    }
    
    @TransactionalException
    public void delDepts(List<SysDept> sysDepts) {
    	for(SysDept sysDept : sysDepts) {
    		List<SysDept> depts = sysDeptMapper.findDeptsByIparentidEquips(sysDept.getIndocno());
    		if(depts != null && depts.size()>=1) {
    			throw new CustomException("base.dept.delete.exist.sub");
    		}else {
    			super.delete(sysDept);
    		}
    	}
    }
    
    public List<SysDept> findFeignAll(SysDeptParam condition){
    	return sysDeptMapper.findFeignAll(condition);
    }
    
    public SysDept findOne(Long indocno) {
        return sysDeptMapper.findOne(indocno);
    }
    
    public List<SysDept> findUserDeptByIparentid(Long iparentid){
        return sysDeptMapper.findDeptsByIparentid(iparentid);
    }    
    
    @TransactionalException
    public SysDept add(SysDept sysDept) {
        AuthInfo authInfo = this.getAuthInfo();
        Long iparentid = sysDept.getIparentid() != null ? sysDept.getIparentid() : 0;
        sysDept.setIparentid(iparentid);
        sysDept.setIcompanyid(authInfo.getIcompanyid());
        sysDept.setIndocno(IDGenerate.getKey(sysDept));
        sysDept = this.setSidccAndLevel(sysDept);
        this.insert(sysDept);
        return sysDept;
    }
    
    @TransactionalException
    public SysDept addNoAuthInfo(SysDept sysDept) {
        Long iparentid = sysDept.getIparentid() != null ? sysDept.getIparentid() : 0;
        sysDept.setIparentid(iparentid);
        sysDept.setIndocno(IDGenerate.getKey(sysDept));
        sysDept = this.setSidccAndLevel(sysDept);
        this.insert(sysDept);
        return sysDept;
    }
    
    @TransactionalException
    public SysDept updateDept(SysDept sysDept) {
    	SysDept oldDept = findOne(sysDept.getIndocno());
    	if(sysDept.getIparentid() ==null) {
    		sysDept.setIparentid(oldDept.getIparentid());
    	}
        sysDept = this.setSidccAndLevel(sysDept);
        this.update(sysDept);
        return sysDept;
    }
    
    /**
     * 拖拽排序
     * @param dIndocno 拖拽对象
     * @param pIndocno 拖拽目标
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @GlobalLockByRedis
    public void dragDept(Long dIndocno, Long pIndocno) 
            throws InstantiationException, IllegalAccessException {
        SysDept dObj = sysDeptMapper.findOne(dIndocno);
        SysDept pObj = sysDeptMapper.findOne(pIndocno);
        dragHelperService.drag(pObj, dObj,true,"iparentid",dObj.getIparentid());
    }
    
    // 插入sidcc
    private SysDept setSidccAndLevel(SysDept sysDept) {
        if(sysDept.getIparentid() != null && sysDept.getIparentid() != 0) {
            SysDept pSysDept = new SysDept();
            pSysDept.setIndocno(sysDept.getIparentid());
            pSysDept = this.select(pSysDept);
            String psidcc = pSysDept.getSidcc() != null ? pSysDept.getSidcc() : "/";
            sysDept.setSidcc( psidcc + sysDept.getIndocno() + "/");
            Integer ilevel = pSysDept.getIlevel() != null ? pSysDept.getIlevel() : 0;
            sysDept.setIlevel(ilevel + 1);
        }else {
            sysDept.setSidcc("/" + sysDept.getIndocno() + "/");
            sysDept.setIlevel(1);
        }
        return sysDept;
    }
    
    private void convertDatadic(SysDept sysDept) {
    	sysDept.setStype(
				sysDatadicHelper.getDatadicNmBySfact(sysDept.getItype(), SysDatadicConstantNameAlias.BASEDEPTTYPE).getSname());
	}
    
}
