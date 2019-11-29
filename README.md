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
    - 取info数据 | ${jsonObj.info} | 输出：json对象转字符串
  
3. 字符串转义
    > jsonObj = JSONObject({"info":"字符串转义"})
    - 字符串转义 | ${toJSONString(jsonObj)?js_string} | 输出：{\\"info\":\\"字符串转json对象\\"}

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
    > year：年, date：日， 支持正、负整数
    - ${makeDateByDelta(year, date)} | 输出: 1988-02-11
    
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
    - <#list premiumSettings as o>${jobType}<#if o_has_next>,</#if></#list> | 输出：010000,100000
 
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
