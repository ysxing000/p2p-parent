<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>雷氏集团-P2P平台(系统管理平台)</title>
    <#include "../common/header.ftl"/>
    <script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
    <script type="text/javascript" src="/js/plugins/jquery.twbsPagination.min.js"></script>
    <script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js" ></script>

    <script type="text/javascript">
        $(function() {

            $("#beginDate,#endDate").click(function(){
                WdatePicker();
            });


            $('#pagination').twbsPagination({
                totalPages : ${pageResult.totalPage}||1,
                startPage : ${pageResult.currentPage},
            visiblePages : 5,
                first : "首页",
                prev : "上一页",
                next : "下一页",
                last : "最后一页",
                onPageClick : function(event, page) {

                $("#currentPage").val(page);
                $("#searchForm").submit();
            }
        });

            $(".auditClass").click(function(){
                $("#editform")[0].reset();
                var data=$(this).data("json");
                $("#myModal [name=id]").val(data.id);
                $("#myModal [name=username]").html(data.username);
                $("#myModal [name=title]").html(data.title);
                $("#myModal [name=bidRequestAmount]").html(data.bidRequestAmount);
                $("#myModal [name=currentRate]").html(data.currentRate);
                $("#myModal [name=monthes2Return]").html(data.monthes2Return);
                $("#myModal [name=returnType]").html(data.returnType);
                $("#myModal [name=totalRewardAmount]").html(data.totalRewardAmount);
                $("#myModal").modal("show");
            });

            $(".btn_audit").click(function(){
                var form=$("#editform");
                form.find("[name=state]").val($(this).val());
                $("#myModal").modal("hide");
                form.ajaxSubmit(function(data){
                    if(data.code==200){
                        $.messager.confirm("提示","审核成功!",function(){
                            window.location.reload();
                        });
                    }
                });
                return false;
            });
        });
    </script>
</head>
<body>
<div class="container">
    <#include "../common/top.ftl"/>
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="bidrequest_audit1_list" />
            <#include "../common/menu.ftl" />
        </div>
        <div class="col-sm-9">
            <div class="page-header">
                <h3>满标一审核管理</h3>
            </div>
            <form id="searchForm" class="form-inline" method="post" action="/bidrequest_audit1_list">


                <div class="form-group">
                    <label>申请时间</label>
                    <input type="hidden" id="currentPage"  name="currentPage" value="1">
                    <input class="form-control" style="width: 130px;" type="text" name="beginDate" id="beginDate" value="${(qo.beginDate?string('yyyy-MM-dd'))!''}" />到
                    <input class="form-control" style="width: 130px;" type="text"name="endDate" id="endDate" value="${(qo.endDate?string('yyyy-MM-dd'))!''}" />
                </div>
                <div class="form-group">
                    <button id="query" class="btn btn-success">
                        <i class="icon-search"></i> 查询
                    </button>
                </div>

            </form>
            <div class="panel panel-default">
                <table class="table">
                    <thead>
                    <tr>
                        <th>标题</th>
                        <th>借款人</th>
                        <th>申请时间</th>
                        <th>借款金额(元)</th>
                        <th>期限</th>
                        <th>利率</th>
                        <th>总利息</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list pageResult.result as data>
                        <tr>
                        <td>
                        <a target="_blank" href="/borrow_info.do?id=${data.id}">${data.title}</a>&emsp;<span class="label label-primary">信</span>
                        </td>
                        <td>${data.createUser.username}</td>
                        <td>${(data.applyTime)!'未发布'}</td>
                        <td>${data.bidRequestAmount}</td>
                        <td>${data.monthes2Return}月</td>
                        <td>${data.currentRate}%</td>
                        <td>${data.totalRewardAmount}</td>
                        <td>${data.bidRequestStateDisplay}</td>
                        <td>
                    <a href="javascript:void(-1);" class="auditClass" data-json='${data.jsonString}'>审核</a>
                        </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
                <div style="text-align: center;">
                    <ul id="pagination" class="pagination"></ul>
                </div>
            </div>
        </div>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-body">
                    <form class="form-horizontal" id="editform" method="post" action="/manbiaoyi">
                        <fieldset>
                            <div id="legend" class="">
                                <legend>满标一审核</legend>
                            </div>
                            <input type="hidden" name="auditorId" value="1" />
                            <input type="hidden" name="id" value="" />
                            <input type="hidden" name="state" value="" />
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="title">标题</label>
                                <div class="col-sm-6">
                                    <label class="form-control" name="title"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="username">借款人</label>
                                <div class="col-sm-6">
                                    <label class="form-control" name="username"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="bidrequestAmount">借款金额(元)</label>
                                <div class="col-sm-6">
                                    <label class="form-control" name="bidRequestAmount"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="monthes2Return">期限</label>
                                <div class="col-sm-6">
                                    <label class="form-control" name="monthes2Return"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="currentRate">还款方式</label>
                                <div class="col-sm-6">
                                    <label class="form-control" name="returnType"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="currentRate">利率</label>
                                <div class="col-sm-6">
                                    <label class="form-control" name="currentRate"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="totalRewardAmount">总利息</label>
                                <div class="col-sm-6">
                                    <label class="form-control" name="totalRewardAmount"></label>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" for="name">审核备注</label>
                                <div class="col-sm-6">
                                    <textarea name="remark" rows="4" cols="60"></textarea>
                                </div>
                            </div>
                        </fieldset>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-success btn_audit" value="5" >审核通过</button>
                    <button type="button" class="btn btn-warning btn_audit" value="6">审核拒绝</button>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>