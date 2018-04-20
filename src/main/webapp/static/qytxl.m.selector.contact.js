function contactSelectorOpen(config){
  //初始化
  init();

  function init(){
    
    if(config.iframe.domId!=''){
      setIframe();
    }

    //监听返回结果
    window.addEventListener('message', msg, false);

    function msg(e){
      var result= JSON.parse(e.data);
      var ifr=document.getElementById(config.iframe.domId);
      switch(result['target']){
        case 'webReady'://选择器准备就绪
          ifr.contentWindow.postMessage(JSON.stringify({target:'initSelector',data:config}),'*');
          break;
        case 'selectCallback':
          if(typeof config.callback=='function'){
            config.callback(result.data);
            window.removeEventListener('message', msg);
          }
          break;
        default:
          console.log('没有找到目标方法');
          break;
      }
    }
  }

  //设置iframe参数
  function setIframe(){
      var iframe=document.getElementById(config.iframe.domId);
      if(iframe!=null){//存在该idiframe
        iframe.style.display = 'block';
        iframe.src=config.tokenUrl;
        iframe.width==''&&(iframe.width='100%');
        iframe.height==''&&(iframe.height='100%');
        iframe.scrolling==''&&(iframe.scrolling='no');
      } else {
          console.log('不存在该id：'+config.iframe.domId+'的iframe');
      }
  }
}
