<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="MainMenu">
	<ol>
		<c:forEach var="topBar" items="${topBar}">
			<li class="down"><a href="#">${topBar.menuOptionName}
					&#11167;</a> <!-- ${topBar.menuOptionName}  --> <c:forEach var="subBar"
					items="${subBar}">

					<c:choose>
						<c:when
							test="${subBar.subMenu==topBar.menuOptionName && subBar.roleName==topBar.roleName}">
							<ul>
								<li class="left"><a href="#">${subBar.menuOptionName}
										&#11166;</a>
									<ol>
										<c:forEach var="Opt" items="${Opt}">
											<c:choose>
												<c:when
													test="${Opt.subMenu==subBar.menuOptionName  && Opt.roleName==topBar.roleName}">

													<li><a
														href="${pageContext.request.contextPath}/${Opt.path}">${Opt.menuOptionName}</a>
													</li>

												</c:when>
											</c:choose>

										</c:forEach>
									</ol></li>
							</ul>
						</c:when>
					</c:choose>

				</c:forEach> <c:forEach var="Opt" items="${Opt}">

					<c:choose>
						<c:when test="${Opt.subMenu==topBar.menuOptionName}">
							<ul>
								<li><a
									href="${pageContext.request.contextPath}/${Opt.path}">${Opt.menuOptionName}</a>
								</li>
							</ul>
						</c:when>
					</c:choose>

				</c:forEach></li>
		</c:forEach>

	</ol>
	<!-- logout 'button' -->
	<div class="logout">
		<a href="${pageContext.request.contextPath}/logout">
			${signOutLabel} </a>
	</div>
</div>

