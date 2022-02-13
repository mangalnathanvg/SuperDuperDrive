package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface CredentialMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE userId = ${userId}")
    ArrayList<Credential> findAllUserCredentials(int userId);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password,  userId) VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    void addUserCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, key = #{key}, password=#{password} WHERE credentialId=#{credentialId}")
    void updateUserCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredential(int credentialId);
}
