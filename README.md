#FreeMarker常用语法


##常用案例
1. 字符串转json对象
    > jsonStr = "{\\"info\\":\\"字符串转json对象\\"}" 
    - 字符串转json对象 | ${toJSON(jsonStr)} 或 ${jsonStr?eval}
    - ${jsonStr} | 输出：{"info":"字符串转json对象"}
    - 取info数据 | ${toJSON(jsonStr).info} | 输出：字符串转json对象   

2. json对象转字符串
    > jsonObj = JSONObject({"info":"json对象转字符串"})
    - json对象转字符串 | ${toJSONString(jsonObj)} | 输出: {"info":"json对象转字符串"}
    - json对象转字符串（格式化输出） | toJSONString({"a":1,"b":2}, 1) | 输出: 
     ```json
       {
           "info":"json对象转字符串"
       }
     ```
    - 取info数据 | ${jsonObj.info} | 输出：json对象转字符串
  
3. 字符串转义
    > jsonObj = JSONObject({"info":"字符串转义"})
    - 字符串转义 | ${toJSONString(jsonObj)?j_string} | 输出：{\\"info\":\\"字符串转json对象\\"}

4. 时间
    - 当前时间(2019-11-18 15:02:53) | ${.now}  
    - 当前时间戳 | ${.now?long} 
    - 今天凌晨时间戳 | ${.now?string('yyyy-MM-dd')?date('yyyy-MM-dd')?long}
    - 后天凌晨时间戳 | ${.now?string('yyyy-MM-dd')?date('yyyy-MM-dd')?long+3600*1000*24*2}
    - yyyy-MM-dd时间格式(2019-11-21) | ${.now?date}
    - 上个月 | ${.now?string["MM"]?number-1} 

5. 根据年龄获取生日
    > age: 年龄
    - ${getBirthday(age)} | 输出: 1988-02-11
    
6. 根据当前时间获取日期
    > year：年差值, month:月差值，date：日差值， 支持正、负整数
    - ${getDateByDelta(year)} | 输出: 1988-02-11
    - ${getDateByDelta(year, month)} | 输出: 1988-02-11
    - ${getDateByDelta(year, month, date)} | 输出: 1988-02-11
    
7. 生成数字列表
    > ${range(start, end)}, start: 开始，end:结束, 支持整数，返回list对象
    - ${range(1, 4)} [1,2,3,4], 该返回值是list对象，无法直接输出
    - ${toJSONString(range(1, 4))} | 输出: [1,2,3,4]   
    - ${toJSONString(1..4)} | 输出: [1,2,3,4]  

8. URL参数提取
    > url = "http://www.baidu.com/getData?searchKey=freeMarker"
    - 提取path | ${urlExtract(url).getPath()} | 输出：http://www.baidu.com/getData
    - 提取参数 | ${urlExtract(url).getParameter('searchKey')} | 输出：freeMarker
                
9. cookies处理
    > 下面函数都输出: JSESSIONID=Gnj-mixq9v8lFSbaBRg7LJOIxkkQs2LPyTOhRgkOBgDfU55Mo15N!952609197;userId=11233
    - list对象处理 | ${toCookieString(["JSESSIONID=Gnj-mixq9v8lFSbaBRg7LJOIxkkQs2LPyTOhRgkOBgDfU55Mo15N!952609197","userId=11233"])}
    - Map对象处理 | ${toCookieString({"JSESSIONID":"Gnj-mixq9v8lFSbaBRg7LJOIxkkQs2LPyTOhRgkOBgDfU55Mo15N!952609197","userId":"11233"})}
    
10. 随机数
    - ${random()} | 输出: 0-1的小数

11. 笛卡尔函数
    > 生成不同参数的多种组合, 返回list对象，输出需要使用`toJSONString`函数
    - ${toJSONString(descartes('a',[1,2],'b',[5,6]))} | 输出：[{"a":1,"b":5},{"a":1,"b":6},{"a":2,"b":5},{"a":2,"b":6}]
    - ${toJSONString(descartes('a',"[1,2]",'b',"[1,2]"))} | 输出：[{"a":1,"b":5},{"a":1,"b":6},{"a":2,"b":5},{"a":2,"b":6}]

12. 生成身份证号
    > ${generateIdNum(birthdayString,sexString)}
    - ${generateIdNum("1987-09-12",'1')} | 输出：330521198709121121
    
13. 提取json对象中符合条件的数据
    ```json
        {
            "premiumSettings":[
                {
                    "clauseDescription":"意外身故、伤残保障20万元;猝死20万元;意外医疗保障2万元;意外住院津贴100元/天;",
                    "addPremiumPerPersonOriginal":189,
                    "addPremiumPerPerson":189,
                    "refundPremiumPerPersonOriginal":141.75,
                    "jobType":"010000",
                    "refundPremiumPerPerson":141.75
                },
                {
                    "clauseDescription":"意外身故、伤残保障20万元;猝死20万元;意外医疗保障2万元;意外住院津贴100元/天;",
                    "addPremiumPerPersonOriginal":147.5,
                    "addPremiumPerPerson":147.5,
                    "refundPremiumPerPersonOriginal":110.63,
                    "jobType":"100000",
                    "refundPremiumPerPerson":110.63
                }
            ]
        }
    
    ```
    - <#list premiumSettings as o><#if o.jobType=='010000'>${o.addPremiumPerPersonOriginal}</#if></#list> | 输出：189
    - <#list premiumSettings as o>${o.jobType}<#if o_has_next>,</#if></#list> | 输出：010000,100000
    - ${listByKey(premiumSettings,'jobType')} | 输出：010000,100000
 
14. list截取
    > list = [1,2,3,4,5,6]
    - 截取前3个数据 | ${toJSONString(list[0..2])} | 输出：[1,2,3]
    
15. 从json对象中提取key为数字或数字开头的值
    ```json
     {
        "a":{"1000":"1000Value"},
        "1b": "1bValue"
    }
    ```
    - 提取a.1000的值 | ${a['1000']} | 输出: 1000Value
    - 提取1b的值 | ${.vars['1b]} | 输出: 1bValue
 
16. JsonPath提取数据
    > jsonObj = JSONObject({"data":{"info":"JsonPath提取数据"}})
    - 提取info的值 | ${jsonPathExtract(data, '$.info')} | 输出: JsonPath提取数据 

17. 数字四舍五入
    > a = 1.225
    - 保留2位小数（四合五入）, 使用freemarker内置函数 | ${a?string('0.##;;roundingMode=halfUp')} | 输出: 1.23
    - 保留2位小数（四合五入）, 使用自定义函数 | ${round(a,2)} | 输出: 1.23
    - 取整（四合五入）, 使用freemarker内置函数 | ${a?round} 或 ${a?string('#;;roundingMode=halfUp')  | 输出: 1
    - 取整（四合五入）, 使用自定义函数 | ${round(a)} | 输出: 1

18. HTML页面数据解析
    ```html
        <div class="btn-group" role="group" aria-label="Basic example">
        <button id="left" type="button" class="btn btn-secondary">Left</button>
        <button id="middle" type="button" class="btn btn-secondary">Middle</button>
        <button id="right" type="button" class="btn btn-secondary">Right</button>
        </div>
    ```
    - 提取id="left"的值,使用自定义函数 | ${jsoup(html).getElementById('left').html()} | 输出:Left
    - 提取id="left"的值,使用自定义函数 | ${jsoupXpath(html).selOne('//div/button[@id="left"]/text()')} | 输出:Left

19. md5加密
    > data="123456"
    - 加密 | ${md5().md5Hex(data)} | 输出: e10adc3949ba59abbe56e057f20f883e
    
20. hmac加密
    > data="123456", key="abc"
    - 加密 | ${hmac().hmacMd5Hex(key, data)} | 输出: 8c7498982f41b93eb0ce8216b48ba21d
    
21. aes加密/解密
    ```text
      加密模式：CBC, 填充：PKCS5Padding, 输出: base64
    ```   
    > data="123456"  key="1234567890123456"  iv(偏移量)="abcdefghabcdefgh"
    - 加密 | ${aes(key,iv).encrypt(data)} | 输出: YlUzT7G/2QOsvT/jbVAcYg==
    > data="YlUzT7G/2QOsvT/jbVAcYg=="  key="1234567890123456"  iv(偏移量)="abcdefghabcdefgh"
    - 解密 | ${aes(key,iv).decrypt(data)} | 输出: 123456
    
22. RSA加密/解密
    > data="123456"  publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCGyuoPj+ac8Kjz0IpVHJTDIFcGPiOq4Qgnf6YFOrKWFx00RedTFgm1lEyNXRY9qsYiKgILhBKNd7KtCDi2haHm/856LSufjVnORhsg3/7ESt6EHZkhsQQ41s+KOCcosACBszzX3k15tXwz0g4OwRf9T5Wd10s1a+dI5u6XbSlI0wIDAQAB" 
    - 加密 | ${rsa().encrypt(data,publicKey)} | 输出: ICQPhv2xcwA3cbZo5zZGOABojo4NBLfg1C7at8vJDTGn3yvQXhjHJFnU8WPvEI6O9PHhTKxTwAkIYlVxFdCZPri1QgbUaPU476tRYV05X/ytXrAE5OQcPIDTW/mJa4AYjQvDCtUUpFg/KJX7uVxO+wfsZGLJTQ5DJFOvcnfk5vU=
    > data="ICQPhv2xcwA3cbZo5zZGOABojo4NBLfg1C7at8vJDTGn3yvQXhjHJFnU8WPvEI6O9PHhTKxTwAkIYlVxFdCZPri1QgbUaPU476tRYV05X/ytXrAE5OQcPIDTW/mJa4AYjQvDCtUUpFg/KJX7uVxO+wfsZGLJTQ5DJFOvcnfk5vU="  privateKey="MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAIbK6g+P5pzwqPPQilUclMMgVwY+I6rhCCd/pgU6spYXHTRF51MWCbWUTI1dFj2qxiIqAguEEo13sq0IOLaFoeb/znotK5+NWc5GGyDf/sRK3oQdmSGxBDjWz4o4JyiwAIGzPNfeTXm1fDPSDg7BF/1PlZ3XSzVr50jm7pdtKUjTAgMBAAECgYEAgk8WmjbWjjBGVXbs1L1Nv5m7J8jY+HCWToEkAfgt/9WWoRtXN8adWlJk82DjiHhZFVwhH/rtaKAoNwk0spdb3RnhynkYsU7TNDrluC8n1BCPf823pQP45FHT3EKVC1EEGqmx12UfQEUHn3ECt+4+YdAzGieZsfvBaRTHMde8iCkCQQD97hBVNylLTvNUxVq+okBSomYwQdP+avlfcWTTEtQN+Rj4UC+HuFR1Ei5CMq7w+WSdUTOs/hWkG3HM7yOPxeXPAkEAh+Q3372QlZuhKnP/TRvef2T+f5zhLUiZuepnIi+mQ8vomYPHjlYO4LFgccpLfzvOQpsRYN6/tqaBfA/e/k4xvQJAbJntmFSAL7ozbbT+S3vfILheFhOBVFVyE/TOV0u9L8CygjjhKx2V2YAOErfUhDFVa8b33+vHW6l1MV2xiuLrlwJBAIEnD0+PrC30tw1RJ9DSbvonM5Z3tK/EM462UIT2QpBm6U+WJoCKHCPDkYU0neJ0Jcm59Z9atVzUDXaq4uZRzQUCQQCSYqHK6RjlRL7bQMtfuFLxuEQVJhKmUEGhc/9qnFxfV//axD/qTKh1Ri0MfQK05iQjaD1MKN+caO0gDQUfDt75"  
    - 解密 | ${rsa().decrypt(data,privateKey)} | 输出: 123456
 
23. base64加密/解密
    > data="123456"
    - 加密 | ${base64().encode(data)} | 输出: MTIzNDU2
    > data="MTIzNDU2" 
    - 解密 | ${base64().decode(data)} | 输出: 123456

24. tripleDES加密/解密
    ```text
      加密模式：ECB, 填充：PKCS5Padding, 输出: base64
    ```   
    > data="123456" key="A0PADHTAUGS61U02EDAL5MUN"
    - 加密 | ${tripleDES(key).encrypt(data)} | 输出: JzroROdaBZk=
    > data="MTIzNDU2" key="A0PADHTAUGS61U02EDAL5MUN"
    - 解密| ${tripleDES(key).decrypt(data)} | 输出: 123456
    
24. signature 特征值函数
    > data = JSONObject({"info":"json对象特征值"})
    - 计算特征值 | ${signatrue(info)} | 输出：9aea1a78836d5763cfe13f1d30b48010