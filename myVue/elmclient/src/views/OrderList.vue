<template>
    <div class="wrapper">
        <!-- header部分 -->
        <header>
            <p>我的订单</p>
        </header>
        <!-- TEST -->
        <h3>
            TEST:
        </h3>
        <ul class="order">
            <li>{{orderArr.value}}</li>

            
        </ul>

        <!-- 订单列表部分 -->

        <h3>未支付订单信息:</h3>
        <ul class="order">
            <li v-for="(item,index) in orderArr.value" :key="index">
                <div v-if="item.value.orderState===0" >
                    <div class="order-info">
                        <p>
                            {{ item.business.businessName }}
                            <i class="fa fa-caret-down" @click="detailetShow(item)"></i>
                        </p>
                        <div class="order-info-right">
                            <p>&#165;{{ item.orderTotal }}</p>
                            <div class="order-info-right-icon">去支付</div>
                        </div>
                    </div>
                </div>
                <ul class="order-detailet" v-show="item.isShowDetailet">
                    <li v-for="odItem in item.list">
                        <p>{{ odItem.food.foodName }} x {{ odItem.quantity }}</p>
                        <p>&#165;{{ odItem.food.foodPrice * odItem.quantity }}</p>
                    </li>
                    <li>
                        <p>配送费</p>
                        <p>
                            &#165;{{ item.business.deliveryPrice }}</p>
                    </li>
                </ul>
            </li>
        </ul>
        <h3>已支付订单信息:</h3>
        <ul class="order">
            <li v-for="(item,index) in orderArr.value" :key="index">
                <div v-if="item.value.orderState===1">
                    <div class="order-info">
                        <p>
                            <i class="fa fa-caret-down" @click="detailetShow(item)"></i>
                        </p>
                        <div class="order-info-right">
                            <p>&#165;
                                {{ item.orderTotal }}</p>
                        </div>
                    </div>
                </div>
                <ul class="order-detailet" v-show="item.isShowDetailet">
                    <li v-for="odItem in item.list">
                        <p>{{ odItem.food.foodName }} x {{ odItem.quantity }}</p>
                        <p>&#165;{{ odItem.food.foodPrice * odItem.quantity }}</p>
                    </li>
                    <li>
                        <p>配送费</p>
                        <p>
                            &#165;{{ item.business.deliveryPrice }}
                        </p>
                    </li>
                </ul>
            </li>
        </ul>
        <!-- 底部菜单部分 -->
        <Footer></Footer>
    </div>
</template>
<script>
// import Footer from '../components/Footer.vue';
// export default {
//     name: 'OrderList',
//     data() {
//         return {
//             orderArr: [],
//             user: {}
//         }
//     },
//     created() {
//         this.user = this.$getSessionStorage('user');
//         this.$axios.post('OrdersController/listOrdersByUserId', this.$qs.stringify({
//             userId: this.user.userId
//         })).then(response => {
//             let result = response.data;
//             for (let orders of result) {
//                 orders.isShowDetailet = false;
//             }
//             this.orderArr = result;
//         }).catch(error => {
//             console.error(error);
//         });
//     }, methods: {
//         detailetShow(orders) {
//             orders.isShowDetailet = !orders.isShowDetailet;
//         }
//     },
//     components: {
//         Footer
//     }
// }


import { ref, onMounted, inject, onBeforeMount } from 'vue';
import Footer from '../components/Footer.vue';
import axios from 'axios'; // 添加这一行来引入 axios
import qs from 'qs';
import { useRoute, useRouter } from 'vue-router';

export default {
    name: 'OrderList',
    components: {
        Footer
    },
    setup() {
        const $getSessionStorage = inject('$getSessionStorage');
        const orderArr = ref([{}]);
        const user = ref({});
        // const orderLoad = ref(false);


        // const $axios = inject('$axios');
        // const $qs = inject('$qs');

        onBeforeMount(() => {
            user.value = $getSessionStorage('user');
            axios.post('OrdersController/listOrdersByUserId', qs.stringify({
                userId: user.value.userId
            })).then(response => {
                // console.log("console.log(response.data);");
                // console.log(response.data);
                // const result = response.data.map(order => {
                //     return { ...order, isShowDetailet: false };
                // });
                // orderArr.value = result;
                // orderArr.value = response.data;
                let result = response.data;
                // console.log(result);
                for (let orders of result) {
                    orders.isShowDetailet = false;
                    console.log(orders);
                }
                orderArr.value = result;
                console.log(orderArr.value);
                // console.log(orderArr);
                // console.log(">>>>>>>>>>>>>>>");

                // orderLoad.value = true;

                // console.log(orderLoad.value);
                // for (let i of orderArr.value) {
                //     console.log(i.orderState);
                // }
            }).catch(error => {
                console.error(error);
            });
        });

        const detailetShow = (orders) => {
            orders.isShowDetailet = !orders.isShowDetailet;
        }

        return {
            orderArr,
            user,
            // orderLoad,
            detailetShow
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

/****************** 历史订单列表部分 ******************/
.wrapper h3 {
    margin-top: 12vw;
    box-sizing: border-box;
    padding: 4vw;
    font-size: 4vw;
    font-weight: 300;
    color: #999;
}

.wrapper .order {
    width: 100%;
}

.wrapper .order li {
    width: 100%;
}

.wrapper .order li .order-info {
    box-sizing: border-box;
    padding: 2vw 4vw;
    font-size: 4vw;
    color: #666;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.wrapper .order li .order-info .order-info-right {
    display: flex;
}

.wrapper .order li .order-info .order-info-right .order-info-right-icon {
    background-color: #f90;
    color: #fff;
    border-radius: 3px;
    margin-left: 2vw;
    user-select: none;
    cursor: pointer;
}

.wrapper .order li .order-detailet {
    width: 100%;
}

.wrapper .order li .order-detailet li {
    width: 100%;
    box-sizing: border-box;
    padding: 1vw 4vw;
    color: #666;
    font-size: 3vw;
    display: flex;
    justify-content: space-between;
    align-items: center;
}
</style>