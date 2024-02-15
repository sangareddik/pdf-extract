<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
var _contextPath = '${pageContext.request.contextPath}';
var _apiGatewayUrl = '${apiGatewayUrl}';
var _mbsAppName= '${mbsAppName}';
var _activeProfiles= '${activeProfiles}';
var _isApiGateway = '${isApiGateway}';
var _accessTokenValidititySeconds = '${accessTokenValidititySeconds}';
var _refreshTokenValiditySeconds = '${refreshTokenValiditySeconds}';
var _webIdleSessionTimeOutSeconds ='${webIdleSessionTimeOutSeconds}';
var _enableSecureCookie = '${enableSecureCookie}';
var _enableHttpOnlyCookie = '${enableHttpOnlyCookie}';
var _verticalLinesEnable = '${verticalLinesEnable}'

window.onload = function() {
	if(document.getElementById('transition-loading-effect'))
 	document.getElementById('transition-loading-effect').style.display='none'
};
</script>
 <style>
body {
    background: #595BD4;
    background: #00578e;
    font-family: 'Titillium Web', sans-serif;
}
#transition-loading-effect {
    position: absolute;
    left: 0;
    right: 0;
    top: 50%;
    width: 200px;
    color: #FFF;
    margin: auto;
    -webkit-transform: translateY(-50%);
    -moz-transform: translateY(-50%);
    -o-transform: translateY(-50%);
    transform: translateY(-50%);
}
#transition-loading-effect span {
    position: absolute;
    height: 30px;
    width: 184px;
    top: 50px;
    overflow: hidden;
}
#transition-loading-effect span > i {
    position: absolute;
    height: 6px;
    width: 6px;
    border-radius: 50%;
    -webkit-animation: wait 4s infinite;
    -moz-animation: wait 4s infinite;
    -o-animation: wait 4s infinite;
    animation: wait 4s infinite;
}
#transition-loading-effect span > i:nth-of-type(1) {
    left: -28px;
    background: yellow;
    background: white;
    
}
#transition-loading-effect span > i:nth-of-type(2) {
    left: -21px;
    -webkit-animation-delay: 0.8s;
    animation-delay: 0.8s;
    background: lightgreen;
}

@-webkit-keyframes wait {
    0%   { left: -7px  }
    30%  { left: 52px  }
    60%  { left: 22px  }
    100% { left: 100px }
}
@-moz-keyframes wait {
    0%   { left: -7px  }
    30%  { left: 52px  }
    60%  { left: 22px  }
    100% { left: 100px }
}
@-o-keyframes wait {
    0%   { left: -7px  }
    30%  { left: 52px  }
    60%  { left: 22px  }
    100% { left: 100px }
}
@keyframes wait {
    0%   { left: -7px  }
    30%  { left: 52px  }
    60%  { left: 22px  }
    100% { left: 100px }
}
    
</style>
<html lang="en">
  <head>
    <meta charset="utf-8" />
        <c:if test="${isApiGateway == true}">
    	    <link rel="shortcut icon" href="<c:url value="/${mbsAppName}/web-app/static/images/favicon.ico"/>" type="image/x-icon">
    	</c:if> 
    	       			
   		 <c:if test="${isApiGateway == false}">
    	    <link rel="shortcut icon" href="<c:url value="/static/images/favicon.ico"/>" type="image/x-icon">
   		 </c:if> 
    <meta
      name="viewport"
      content="width=device-width, initial-scale=1, shrink-to-fit=no"
    />
    <meta name="theme-color" content="#000000" />
    <!--
      manifest.json provides metadata used when your web app is added to the
      homescreen on Android. See https://developers.google.com/web/fundamentals/web-app-manifest/
    -->
    <!--<link rel="manifest" href="%PUBLIC_URL%/manifest.json" /> -->
    <!--
      Notice the use of %PUBLIC_URL% in the tags above.
      It will be replaced with the URL of the `public` folder during the build.
      Only files inside the `public` folder can be referenced from the HTML.

      Unlike "/favicon.ico" or "favicon.ico", "%PUBLIC_URL%/favicon.ico" will
      work correctly both with client-side routing and a non-root public URL.
      Learn how to configure a non-root public URL by running `npm run build`.
    -->
    <title>MBS Expert</title>
  </head>
  <body>
  <noscript>You need to enable JavaScript to run this app.</noscript>
 	       <div id="transition-loading-effect">
            <p>Loading, Please wait...</p>
            <span><i></i><i></i></span>
        </div>
      <div id="root-app"></div>
     <!--
      This HTML file is a template.
      If you open it directly in the browser, you will see an empty page.

      You can add webfonts, meta tags, or analytics to this file.
      The build step will place the bundled scripts into the <body> tag.

      To begin the development, run `npm start` or `yarn start`.
      To create a production bundle, use `npm run build` or `yarn build`.
    -->   
    <c:if test="${isApiGateway == true}">
    	<script charset="utf-8" type="text/javascript" src="<c:url value="/${mbsAppName}/web-app/static/appMain.js"/>"></script>
    </c:if>
    <c:if test="${isApiGateway == false}">
    	<script charset="utf-8" type="text/javascript" src="<c:url value="/static/appMain.js"/>"></script>
    </c:if> 
  </body>
</html>
