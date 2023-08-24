### main.js
```js
//设置axios的基础url部分
axios.defaults.baseURL = 'http://localhost:8080/elm/'; //将axios挂载到vue实例上，使用时就可以 this.$axios 这样使用了 Vue.prototype.$axios = axios;
app.provide('$qs', qs);
app.provide('$getCurDate', getCurDate);
app.provide('$setSessionStorage', setSessionStorage);
app.provide('$getSessionStorage', getSessionStorage);
app.provide('$removeSessionStorage', removeSessionStorage);
app.provide('$setLocalStorage', setLocalStorage);
app.provide('$getLocalStorage', getLocalStorage);
app.provide('$removeLocalStorage', removeLocalStorage);
// Vue.prototype.$qs = qs;
// Vue.prototype.$getCurDate = getCurDate;
// Vue.prototype.$setSessionStorage = setSessionStorage;
// Vue.prototype.$getSessionStorage = getSessionStorage;
// Vue.prototype.$removeSessionStorage = removeSessionStorage;
// Vue.prototype.$setLocalStorage = setLocalStorage;
// Vue.prototype.$getLocalStorage = getLocalStorage;
// Vue.prototype.$removeLocalStorage = removeLocalStorage;

router.beforeEach(function(to,from,next){
    let user = sessionStorage.getItem('user'); //除了登录、注册、首页、商家列表、商家信息之外，都需要判断是否登录 
    if(!(to.path=='/'||to.path=='/index'||to.path=='/businessList'||to.path=='/businessInfo'||to.path=='/login'||to.path=='/register')){
        if(user==null){
            router.push('/login');
            location.reload();
        } 
    }
    next();
});


// new Vue({
//     router,
//     render: h => h(App)
// }).$mount('#app')


app.use(router);
app.mount('#app')
```



### BusinessList.vue
确保在组件的 setup 函数中使用 inject： 在 Vue 3 中，推荐使用setup 函数来编写逻辑，确保你在 setup 函数中使用 inject。如果你在模板中使用了 `this.$getSessionStorage`，那是不正确的。
```vue


```