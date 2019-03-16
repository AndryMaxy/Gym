<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" isELIgnored="false" isErrorPage="true" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="content"/>
<html>
<head>
    <title><fmt:message key="about.title"/></title>
    <c:import url="/WEB-INF/jsp/fragment/header.jsp" charEncoding="utf-8"/>
    <style>
        p {
            margin-right: 50px;
            margin-left: 50px;
            text-align: justify;
            text-indent: 50px;
        }
    </style>
</head>
<body>
<c:import url="/WEB-INF/jsp/fragment/locale.jsp" charEncoding="utf-8"/>
<div class="container mainCont">
    <c:import url="/WEB-INF/jsp/fragment/hat.jsp" charEncoding="utf-8"/>
    <div style="margin-top: 50px; font-size: 16pt">
        <c:choose>
            <c:when test="${sessionScope.locale.toLanguageTag() == 'ru-RU'}">
                <p>Мы позаботились о комфорте наших посетителей и сделали большой центр площадью более 600 кв.м., что
                    позволило создать атмосферу уюта. У нас не возникает очередей на тренажеры, и все могут свободно
                    заниматься, не создавая помех друг другу.</p>
                <p>Новое, современное, специализированное покрытие полов, созданное для того, чтобы исключить растяжения
                    и неудобства во время занятий спортом, и Вам не о чем беспокоиться.</p>

                <p>Мы идем в ногу со временем, отвечаем всем изменениям, которые происходят в фитнес индустрии Минска,
                    дарим своим клиентам все самое интересное, модное и уникальное, что может предложить сегодня фитнес.
                    Мы способны объединить и увлечь за собой людей абсолютно разного возраста, разных профессий и
                    интересов. Мы – команда профессионалов-единомышленников, увлеченных своей работой, стремящихся
                    творить, дарить красоту, радость, зажигать стремлением достигнуть лучшего и большего.</p>
            </c:when>
            <c:when test="${sessionScope.locale.toLanguageTag() == 'be-BY'}">
                <p>Мы паклапаціліся пра камфорт нашых наведвальнікаў і зрабілі вялікі цэнтр плошчай больш за 600 кв.м.,
                    што дазволіла стварыць атмасферу выгоды. У нас не ўзнікае чэргаў на трэнажоры, і ўсе могуць свабодна
                    займацца, не ствараючы перашкод адзін аднаму.</p>

                <p>Новае, сучаснае, спецыялізаванае пакрыццё падлог, створанае для таго, каб выключыць расцяжэння і
                    нязручнасці падчас заняткаў спортам, і Вам няма пра што турбавацца.</p>

                <p>Мы ідзем у нагу з часам, адказваем ўсім зменаў, якія адбываюцца ў фітнес індустрыі Мінска, дорым
                    сваім кліентам усё самае цікавае, моднае і ўнікальнае, што можа прапанаваць сёння фітнес. Мы
                    здольныя аб'яднаць і захапіць за сабой людзей абсалютна рознага ўзросту, розных прафесій і
                    інтарэсаў. Мы - каманда прафесіяналаў-аднадумцаў, захопленых сваёй працай, якія імкнуцца тварыць,
                    дарыць прыгажосць, радасць, запальваць імкненнем дасягнуць лепшага і большага.</p>
            </c:when>
            <c:otherwise>
                <p>We took care of the comfort of our visitors and made a large center of more than 600 square meters,
                    which
                    allowed us to create a cozy atmosphere.
                    We do not have queues for simulators, and everyone can freely practice without interfering with each
                    other.</p>
                <p>New, modern, specialized flooring, designed to eliminate stretching and inconvenience during sports,
                    and you have nothing to worry about.</p>

                <p>We keep up with the times, we respond to all the changes that occur in the fitness industry of Minsk,
                    we give our clients all the most interesting, fashionable and unique that fitness can offer today.
                    We are able to unite and carry along people of absolutely different ages, different professions and
                    interests. We are a team of like-minded professionals, passionate about their work, seeking to
                    create,
                    give beauty, joy, ignite the desire to achieve better and more.</p>
            </c:otherwise>
        </c:choose>
    </div>
    <p style="text-align: center">
        <fmt:message key="common.image" var="img"/>
        <img src="${pageContext.request.contextPath}/resources/img/gym.jpg" alt="${img}"
             style="width: 522px; height: 322px;">
    </p>
</div>
</body>
</html>
