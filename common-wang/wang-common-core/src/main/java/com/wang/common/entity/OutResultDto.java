/**  
 * @Title:  OutResultDto.java
 * @Package com.dowin.entity.customer
 * @Description: 输出信息的基类
 * @author administrator
 * @date  2016年4月19日 下午8:59:19
 * @version V1.0  
 * Update Logs:
 * ****************************************************
 * Name:
 * Date:
 * Description:
 ******************************************************
 */
package com.wang.common.entity;

import java.io.Serializable;

import com.wang.common.enums.OutResultEnum.OutStatusEnum;


/**
 * @ClassName: OutResultDto
 * @Description: 输出信息的基类
 * @author administrator
 * @date 2016年4月19日 下午8:59:19
 *
 */
public class OutResultDto implements Serializable
{
    private static final long serialVersionUID = 8459005838549759900L;
    private String status;// 成功：1 失败：0
    private String code;// 错误码
    private String msg;// 失败时返回错误信息
    private Object data;
    
    public OutResultDto(String code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public OutResultDto(Object data) {
		this.code = OutStatusEnum.FAILURE.toString();
		this.msg = "";
		this.data = data;
	}

    public OutResultDto()
    {
        this.status = OutStatusEnum.FAILURE.toString();
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
    
    

}
