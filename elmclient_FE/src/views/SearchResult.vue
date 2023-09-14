<template>
    <div class="wrapper">
        <!-- header部分 -->
        <header>
            <p>搜索结果</p>
        </header>
        <!-- 商家列表部分 -->
        <ul class="business">
            <li v-for="item in businessArr" @click="toBusinessInfo(item.businessId)">
                <div class="business-img">
                    <img :src="item.businessImg">
                    <div class="business-img-quantity" v-show="item.quantity > 0">
                        {{ item.quantity }}</div>
                </div>
                <div class="business-info">
                    <h3>{{ item.businessName }}</h3>
                    <p>&#165;{{ item.starPrice }}起送 | &#165;{{ item.deliveryPrice }}配送</p>
                    <p>{{ item.businessExplain }}</p>
                </div>
            </li>
        </ul>
        <!-- 底部菜单部分 -->
        <Footer></Footer>
    </div>
</template>



<script>
//API
//listBusinessByBusinessName
import Footer from '../components/Footer.vue';
import { ref, onMounted, inject, getCurrentInstance } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios'; // 添加这一行来引入 axios
import qs from 'qs';

export default {
    name: 'SearchResult',
    components: {
        Footer
    },
    setup() {
        const $getSessionStorage = inject('$getSessionStorage'); // Inject the $getSessionStorage method
        const router = useRouter();
        const route = useRoute();
        const serachTxt = ref('');
        const businessArr = ref([]);
        const user = ref({});

        onMounted(() => {      
            serachTxt.value = route.query.bussinessName;
            // console.log(serachTxt.value);

            user.value = $getSessionStorage('user');
            // alert(serachTxt.value);
            
            //根据serachTxt查询商家信息 
            axios.post('BusinessController/listBusinessByBusinessName', qs.stringify({ 
                businessName: serachTxt.value 
            })).then(response => {
                businessArr.value = response.data; //判断是否登录 
                // alert(businessArr.value)
                if (user.value != null) {
                    listCart();
                }
            }).catch(error => {
                console.error(error);
            });
        });

        const listCart = () => {
            axios.post('CartController/listCart', qs.stringify({
                userId: user.value.userId
            })).then(response => {
                let cartArr = response.data; //遍历所有食品列表
                for (let businessItem of businessArr.value) {
                    businessItem.quantity = 0;
                    for (let cartItem of cartArr) {
                        if (cartItem.businessId == businessItem.businessId) {
                            businessItem.quantity += cartItem.quantity;
                        }
                    }
                }
                businessArr.value.sort();
            }).catch(error => {
                console.error(error);
            });
        };

        const toBusinessInfo = (businessId) => {
            router.push({ path: '/businessInfo', query: { businessId: businessId } });
        };
        
        return {
            businessArr,
            user,
            listCart,
            toBusinessInfo,
        };
    }
};
</script>
<style scoped>
/****************** 总容器 ******************/
.wrapper {
    width: 100%;
    height: 100%;
}

/****************** header部分 ******************/
.wrapper header {
    width: 100%;
    height: 12vw;
    background-color: #0097FF;
    color: #fff;
    font-size: 4.8vw;
    position: fixed;
    left: 0;
    top: 0;
    z-index: 1000;
    display: flex;
    justify-content: center;
    align-items: center;
}

/****************** 商家列表部分 ******************/
.wrapper .business {
    width: 100%;
    margin-top: 12vw;
    margin-bottom: 14vw;
}

.wrapper .business li {
    width: 100%;
    box-sizing: border-box;
    padding: 2.5vw;
    border-bottom: solid 1px #DDD;
    user-select: none;
    cursor: pointer;
    display: flex;
    align-items: center;
}

.wrapper .business li .business-img {
    /*这里设置为相当定位，成为business-img-quantity元素的父元素*/
    position: relative;
}

.wrapper .business li .business-img img {
    width: 20vw;
    height: 20vw;
}

.wrapper .business li .business-img .business-img-quantity {
    width: 5vw;
    height: 5vw;
    background-color: red;
    color: #fff;
    font-size: 3.6vw;
    border-radius: 2.5vw;
    display: flex;
    justify-content: center;
    align-items: center;
    /*设置成绝对定位，不占文档流空间*/
    position: absolute;
    right: -1.5vw;
    top: -1.5vw;
}

.wrapper .business li .business-info {
    margin-left: 3vw;
}

.wrapper .business li .business-info h3 {
    font-size: 3.8vw;
    color: #555;
}

.wrapper .business li .business-info p {
    font-size: 3vw;
    color: #888;
    margin-top: 2vw;
}
</style>