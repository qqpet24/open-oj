package com.xmu.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xmu.auth.domain.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author summer
 * @see <a href=""></a><br/>
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
