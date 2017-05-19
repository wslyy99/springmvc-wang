package com.wang.entity.demo;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlTransient;
import com.alibaba.fastjson.annotation.JSONField;
import com.wang.common.page.Page;

public class Demo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5616090442969139037L;

	private Integer id;
	
    private String name;

    @JSONField (format="yyyy-MM-dd HH:mm:ss")
    private Date createtime;

    private String deleteflag;
    
    /**
	 * 当前实体分页对象
	 */
	protected Page page;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(String deleteflag) {
        this.deleteflag = deleteflag == null ? null : deleteflag.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
	
	@JSONField(serialize = false)
    @XmlTransient
    public Page getPage() {
		if(page==null)
			page = new Page();
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}