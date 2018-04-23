<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
 <!-- content start -->
  <div class="admin-content">

    <div class="am-cf am-padding">
      <div class="am-fl am-cf"><strong class="am-text-primary am-text-lg">比赛</strong> / <small>Contest</small></div>
    </div>

    <ul class=" am-avg-lg-1 am-margin gallery-list">
		<c:forEach items="${contestList}" var="contest">
			<li>
					<table class="am-table am-table-hover table-main">
						<tbody>
							<tr bgcolor="#7EC0EE">
								<th colspan="4"><font color="black">${contest.title}</font></th>
							</tr>
							<tr>
								<th>报名人数:</th>
								<td>${contest.joinNumber}</td>
								<th>比赛时长:</th>
								<td>${contest.timeLimit}</td>
							</tr>
							<tr>
								<th>开始时间:</th>
								<td><fmt:formatDate value="${contest.startTime }" pattern="yyyy-MM-dd hh:mm"/></td>
								
								<th>报名开始－结束时间:</th>
								<td><fmt:formatDate value="${contest.startJoinTime }" pattern="yyyy-MM-dd hh:mm"/>－
								<fmt:formatDate value="${contest.endJoinTime }" pattern="yyyy-MM-dd hh:mm"/></td>
							</tr>
							<tr>
								<th>比赛状态:</th>
							<c:choose>
								<c:when test="${contest.status==0}">
									<td>未开始</td>
								</c:when>
								<c:when test="${contest.status==1}">
									<td>正在进行</td>
								</c:when>
								<c:otherwise>
									<td>已经结束</td>
								</c:otherwise>
							</c:choose>
								<th> </th>
								<td>
									<a href="${pageContext.request.contextPath}/contest/${contest.contestId}"><font color="blue"> 查看详情</font></a>
								</td>
							</tr>
								
							
						</tbody>
					</table>
					<hr />
			</li>
		</c:forEach>
    </ul>
       <!-- 分页 -->
   共 x 条记录
  <div class="am-fr">
    <ul class="am-pagination">
     	${pagination}
    </ul>
  </div>
  </div>
  

  <!-- content end -->
