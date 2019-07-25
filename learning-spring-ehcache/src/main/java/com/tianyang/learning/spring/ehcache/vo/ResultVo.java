package com.tianyang.learning.spring.ehcache.vo;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @author yttiany
 * @author yttiany
 * @Description: rest接口层返回对象
 * @ProjectName: self-working-space
 * @Package: com.tianyang.learning.spring.ehcache.vo
 * @Date Create on 2019/7/25.10:13
 * -----------------------------------------------------------.
 * @Modify - - - - - - - - - - - - - - - - - -
 * @ModTime 2019/7/25.10:13
 * @ModDesc:
 * @Modify - - - - - - - - - - - - - - - - - -
 * -----------------------------------------------------------
 */
public class ResultVo implements Serializable {
    private static final long serialVersionUID = 6543751866024162628L;
    private String kind;
    private boolean result;
    private String msg;
    private Object data;

    public ResultVo() {
        this.result = true;
    }

    public ResultVo(boolean result, String kind) {
        this.result = result;
        this.kind = kind;
    }
    
    public ResultVo(boolean result, Object data) {
        this.result = result;
        this.data = data;
    }

    public ResultVo(String kind, String msg) {
        this.result = false;
        this.kind = kind;
        this.msg = msg;
    }

    public ResultVo(boolean result, String kind, String msg) {
        this.result = result;
        this.kind = kind;
        this.msg = msg;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
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

    
	public ResultVo(boolean result, Object data, String kind, String msg) {
		this.result = result;
		this.data = data;
		this.kind = kind;
		this.msg = msg;
	}

    /***
     * 这里重写同String方法是为了可以再net.sf.ehcache.distribution.RMICachePeer的日志中打印对应详细属性
     * @return
     */
    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
