hello,${name}
<#noparse>`${index+1}`</#noparse>
${antd_tableParams!}
<#if hasView>
    if
</#if>
${package?substring(package?last_index_of(".")+1)?lower_case}
<#if antd_exportParamUrl?? && antd_exportParamUrl?length gt 0>
    1
<#else>
    2
</#if>
${module?uncap_first}

<#if object! != ''>
</#if>

<#list primarys as item>record.data.${item}<#if item_has_next>, </#if></#list>

<!-- ??是判断对象是否为空(??是?exists的缩写) -->
如果object不为空，则执行里面语句
<#if object ??>${object}<#if>

它的用法就是忽略list列表当中的空值
<#list lists!! as list></#list>

参考： http://freemarker.foofun.cn/ref_builtins_alphaidx.html
