/*! This file is created by JasonTan */
!function(e){function n(e){delete q[e]}function r(e){var n=document.getElementsByTagName("head")[0],r=document.createElement("script");r.type="text/javascript",r.charset="utf-8",r.src=u.p+""+e+"."+O+".hot-update.js",n.appendChild(r)}function t(e){return e=e||1e4,new Promise(function(n,r){if("undefined"==typeof XMLHttpRequest)return r(new Error("No browser support"));try{var t=new XMLHttpRequest,o=u.p+""+O+".hot-update.json";t.open("GET",o,!0),t.timeout=e,t.send(null)}catch(e){return r(e)}t.onreadystatechange=function(){if(4===t.readyState)if(0===t.status)r(new Error("Manifest request to "+o+" timed out."));else if(404===t.status)n();else if(200!==t.status&&304!==t.status)r(new Error("Manifest request to "+o+" failed."));else{try{var e=JSON.parse(t.responseText)}catch(e){return void r(e)}n(e)}}})}function o(e){var n=U[e];if(!n)return u;var r=function(r){return n.hot.active?(U[r]?U[r].parents.indexOf(e)<0&&U[r].parents.push(e):(E=[e],v=r),n.children.indexOf(r)<0&&n.children.push(r)):E=[],u(r)};for(var t in u)Object.prototype.hasOwnProperty.call(u,t)&&"e"!==t&&Object.defineProperty(r,t,function(e){return{configurable:!0,enumerable:!0,get:function(){return u[e]},set:function(n){u[e]=n}}}(t));return r.e=function(e){function n(){I--,"prepare"===x&&(k[e]||s(e),0===I&&0===H&&l())}return"ready"===x&&i("prepare"),I++,u.e(e).then(n,function(e){throw n(),e})},r}function c(e){var n={_acceptedDependencies:{},_declinedDependencies:{},_selfAccepted:!1,_selfDeclined:!1,_disposeHandlers:[],_main:v!==e,active:!0,accept:function(e,r){if(void 0===e)n._selfAccepted=!0;else if("function"==typeof e)n._selfAccepted=e;else if("object"==typeof e)for(var t=0;t<e.length;t++)n._acceptedDependencies[e[t]]=r||function(){};else n._acceptedDependencies[e]=r||function(){}},decline:function(e){if(void 0===e)n._selfDeclined=!0;else if("object"==typeof e)for(var r=0;r<e.length;r++)n._declinedDependencies[e[r]]=!0;else n._declinedDependencies[e]=!0},dispose:function(e){n._disposeHandlers.push(e)},addDisposeHandler:function(e){n._disposeHandlers.push(e)},removeDisposeHandler:function(e){var r=n._disposeHandlers.indexOf(e);r>=0&&n._disposeHandlers.splice(r,1)},check:a,apply:f,status:function(e){if(!e)return x;P.push(e)},addStatusHandler:function(e){P.push(e)},removeStatusHandler:function(e){var n=P.indexOf(e);n>=0&&P.splice(n,1)},data:D[e]};return v=void 0,n}function i(e){x=e;for(var n=0;n<P.length;n++)P[n].call(null,e)}function d(e){return+e+""===e?+e:e}function a(e){if("idle"!==x)throw new Error("check() is only allowed in idle status");return g=e,i("check"),t(_).then(function(e){if(!e)return i("idle"),null;A={},k={},M=e.c,m=e.h,i("prepare");var n=new Promise(function(e,n){w={resolve:e,reject:n}});b={};for(var r in q)s(r);return"prepare"===x&&0===I&&0===H&&l(),n})}function p(e,n){if(M[e]&&A[e]){A[e]=!1;for(var r in n)Object.prototype.hasOwnProperty.call(n,r)&&(b[r]=n[r]);0==--H&&0===I&&l()}}function s(e){M[e]?(A[e]=!0,H++,r(e)):k[e]=!0}function l(){i("ready");var e=w;if(w=null,e)if(g)Promise.resolve().then(function(){return f(g)}).then(function(n){e.resolve(n)},function(n){e.reject(n)});else{var n=[];for(var r in b)Object.prototype.hasOwnProperty.call(b,r)&&n.push(d(r));e.resolve(n)}}function f(r){function t(e,n){for(var r=0;r<n.length;r++){var t=n[r];e.indexOf(t)<0&&e.push(t)}}if("ready"!==x)throw new Error("apply() is only allowed in ready status");r=r||{};var o,c,a,p,s,l={},f=[],h={},y=function(){};for(var v in b)if(Object.prototype.hasOwnProperty.call(b,v)){s=d(v);var w;w=b[v]?function(e){for(var n=[e],r={},o=n.slice().map(function(e){return{chain:[e],id:e}});o.length>0;){var c=o.pop(),i=c.id,d=c.chain;if((p=U[i])&&!p.hot._selfAccepted){if(p.hot._selfDeclined)return{type:"self-declined",chain:d,moduleId:i};if(p.hot._main)return{type:"unaccepted",chain:d,moduleId:i};for(var a=0;a<p.parents.length;a++){var s=p.parents[a],l=U[s];if(l){if(l.hot._declinedDependencies[i])return{type:"declined",chain:d.concat([s]),moduleId:i,parentId:s};n.indexOf(s)>=0||(l.hot._acceptedDependencies[i]?(r[s]||(r[s]=[]),t(r[s],[i])):(delete r[s],n.push(s),o.push({chain:d.concat([s]),id:s})))}}}}return{type:"accepted",moduleId:e,outdatedModules:n,outdatedDependencies:r}}(s):{type:"disposed",moduleId:v};var g=!1,_=!1,j=!1,P="";switch(w.chain&&(P="\nUpdate propagation: "+w.chain.join(" -> ")),w.type){case"self-declined":r.onDeclined&&r.onDeclined(w),r.ignoreDeclined||(g=new Error("Aborted because of self decline: "+w.moduleId+P));break;case"declined":r.onDeclined&&r.onDeclined(w),r.ignoreDeclined||(g=new Error("Aborted because of declined dependency: "+w.moduleId+" in "+w.parentId+P));break;case"unaccepted":r.onUnaccepted&&r.onUnaccepted(w),r.ignoreUnaccepted||(g=new Error("Aborted because "+s+" is not accepted"+P));break;case"accepted":r.onAccepted&&r.onAccepted(w),_=!0;break;case"disposed":r.onDisposed&&r.onDisposed(w),j=!0;break;default:throw new Error("Unexception type "+w.type)}if(g)return i("abort"),Promise.reject(g);if(_){h[s]=b[s],t(f,w.outdatedModules);for(s in w.outdatedDependencies)Object.prototype.hasOwnProperty.call(w.outdatedDependencies,s)&&(l[s]||(l[s]=[]),t(l[s],w.outdatedDependencies[s]))}j&&(t(f,[w.moduleId]),h[s]=y)}var H=[];for(c=0;c<f.length;c++)s=f[c],U[s]&&U[s].hot._selfAccepted&&H.push({module:s,errorHandler:U[s].hot._selfAccepted});i("dispose"),Object.keys(M).forEach(function(e){!1===M[e]&&n(e)});for(var I,k=f.slice();k.length>0;)if(s=k.pop(),p=U[s]){var A={},q=p.hot._disposeHandlers;for(a=0;a<q.length;a++)(o=q[a])(A);for(D[s]=A,p.hot.active=!1,delete U[s],delete l[s],a=0;a<p.children.length;a++){var S=U[p.children[a]];S&&(I=S.parents.indexOf(s))>=0&&S.parents.splice(I,1)}}var J,N;for(s in l)if(Object.prototype.hasOwnProperty.call(l,s)&&(p=U[s]))for(N=l[s],a=0;a<N.length;a++)J=N[a],(I=p.children.indexOf(J))>=0&&p.children.splice(I,1);i("apply"),O=m;for(s in h)Object.prototype.hasOwnProperty.call(h,s)&&(e[s]=h[s]);var T=null;for(s in l)if(Object.prototype.hasOwnProperty.call(l,s)&&(p=U[s])){N=l[s];var L=[];for(c=0;c<N.length;c++)if(J=N[c],o=p.hot._acceptedDependencies[J]){if(L.indexOf(o)>=0)continue;L.push(o)}for(c=0;c<L.length;c++){o=L[c];try{o(N)}catch(e){r.onErrored&&r.onErrored({type:"accept-errored",moduleId:s,dependencyId:N[c],error:e}),r.ignoreErrored||T||(T=e)}}}for(c=0;c<H.length;c++){var R=H[c];s=R.module,E=[s];try{u(s)}catch(e){if("function"==typeof R.errorHandler)try{R.errorHandler(e)}catch(n){r.onErrored&&r.onErrored({type:"self-accept-error-handler-errored",moduleId:s,error:n,orginalError:e,originalError:e}),r.ignoreErrored||T||(T=n),T||(T=e)}else r.onErrored&&r.onErrored({type:"self-accept-errored",moduleId:s,error:e}),r.ignoreErrored||T||(T=e)}}return T?(i("fail"),Promise.reject(T)):(i("idle"),new Promise(function(e){e(f)}))}function u(n){if(U[n])return U[n].exports;var r=U[n]={i:n,l:!1,exports:{},hot:c(n),parents:(j=E,E=[],j),children:[]};return e[n].call(r.exports,r,r.exports,o(n)),r.l=!0,r.exports}var h=window.webpackJsonp;window.webpackJsonp=function(n,r,t){for(var o,c,i,d=0,a=[];d<n.length;d++)c=n[d],q[c]&&a.push(q[c][0]),q[c]=0;for(o in r)Object.prototype.hasOwnProperty.call(r,o)&&(e[o]=r[o]);for(h&&h(n,r,t);a.length;)a.shift()();if(t)for(d=0;d<t.length;d++)i=u(u.s=t[d]);return i};var y=window.webpackHotUpdate;window.webpackHotUpdate=function(e,n){p(e,n),y&&y(e,n)};var v,w,b,m,g=!0,O="4e56d31628eff89d26ce",_=1e4,D={},E=[],j=[],P=[],x="idle",H=0,I=0,k={},A={},M={},U={},q={1:0};u.m=e,u.c=U,u.d=function(e,n,r){u.o(e,n)||Object.defineProperty(e,n,{configurable:!1,enumerable:!0,get:r})},u.n=function(e){var n=e&&e.__esModule?function(){return e.default}:function(){return e};return u.d(n,"a",n),n},u.o=function(e,n){return Object.prototype.hasOwnProperty.call(e,n)},u.p="./test/",u.oe=function(e){throw e},u.h=function(){return O}}([]);