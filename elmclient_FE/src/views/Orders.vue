<template>
    <div class="wrapper">
        <!-- header部分 -->
        <header>
            <p>确认订单</p>
        </header>
        <!-- 订单信息部分 -->
        <div class="order-info">
            <h5>订单配送至:</h5>
            <div class="order-info-address" @click="toUserAddress">
                <p>{{ deliveryaddress != null ? deliveryaddress.address : '请选择送货地址' }}</p> <i class="fa fa-angle-right"></i>
            </div>
            <p>{{ user.userName }}{{ user.userSex | sexFilter }} {{ user.userId }}</p>
        </div>
        <h3>{{ business.businessName }}</h3>
        <!-- 订单明细部分 -->
        <ul class="order-detailed">
            <li v-for="item in cartArr">
                <div class="order-detailed-left">
                    <img :src="item.food.foodImg">
                    <p>{{ item.food.foodName }} x {{ item.quantity }}</p>
                </div>
                <p>&#165;{{ item.food.foodPrice * item.quantity }}</p>
            </li>
        </ul>
        <div class="order-deliveryfee">
            <p>配送费</p>
            <p>&#165;{{ business.deliveryPrice }}</p>
        </div>

        <div class="order-deliveryfee">
            <p>可用积分为：{{point}}</p>
            <button @click="toggleUsePoints">{{ usePoints ? '取消使用积分' : '使用积分' }}</button>
        </div>
        <div class="order-deliveryfee" v-if="usePoints">
        <input type="range" min="0" :max="maxPointsUsing" step="100" v-model="usePointsNum">
        <p>选择使用积分数量: {{ usePointsNum }}</p>
        </div>
        <div class="order-deliveryfee">
            <p>积分抵扣金额为</p>
            <p>&#165{{ usePointsNum/100 }}</p>
        </div>

        

        <!-- 合计部分 -->
        <div class="total">
            <div class="total-left">
                &#165;{{ totalPrice }}
            </div>
            <div class="total-right" @click="toPayment">
                去支付
            </div>
        </div>
    </div>
</template>
<script>
import { onMounted, ref, inject, computed } from 'vue';
import axios from 'axios';
import qs from 'qs';
import { useRoute, useRouter } from 'vue-router';
import Footer from '../components/Footer.vue';

export default {
    name: 'Orders',
    components: {
        Footer
    },
    setup() {
        const $getSessionStorage = inject('$getSessionStorage'); // Inject the $getSessionStorage method
        const $getLocalStorage = inject('$getLocalStorage')
        const router = useRouter();
        const route = useRoute();
        const businessId = ref(route.query.businessId);
        const UsedOrderId = ref(route.query.UsedOrderId)
        const business = ref({});
        // const user = ref({});
        const user = ref({
            gender: 1,
            userId: 0,
        });
        const cartArr = ref([]);
        const foodArr = ref([]);
        const deliveryaddress = ref([]);

        //新增的积分部分
        const usePoints = ref(false);// 默认不使用积分
        const usePointsNum = ref(0);//使用积分的数量
        const maxPointsUsing = ref(0);//最大可用积分数量（每满100可以使用100积分）
        const point = ref(0);

        onMounted(()=> {
            user.value = $getSessionStorage('user');
            deliveryaddress.value = $getLocalStorage(user.value.userId);
            //查询当前商家
            axios.post('BusinessController/getBusinessById', qs.stringify({
                businessId: businessId.value
            })).then(response => {
                business.value = response.data;
            }).catch(error => {
                console.error(error);
            });
            //查询当前用户在购物车中的当前商家食品列表 
            
            if(UsedOrderId.value == 0){
                axios.post('CartController/listCart', qs.stringify({
                    userId: user.value.userId,
                    businessId: businessId.value
                })).then(response => {
                    cartArr.value = response.data;
                }).catch(error => {
                    console.error(error);
                });
            }else{
                axios.post('OrdersController/getOrdersById',qs.stringify({
                    orderId:UsedOrderId.value
                })).then(response => {
                    cartArr.value = response.data.list;
                });
            }
            //查询当前用户的积分数量
            axios.post('UserController/getPointById', qs.stringify({
                userId: user.value.userId
            })).then(response =>{
                point.value = response.data;
            }).catch(error => {
                console.error(error);
            });
        })

        const toggleUsePoints = () =>{
            usePoints.value = !usePoints.value;
        }
        const toUserAddress = () => {
            router.push({ path: '/userAddress', query: { businessId: businessId.value } });
        }
        const toPayment = () => {
            if (deliveryaddress.value == null) {
                alert('请选择送货地址!');
                return;
            }
            //创建订单 
            if(UsedOrderId.value != 0){
                //老订单,要根据积分的使用情况去更改
                if(usePoints.value == true){
                    axios.post('OrdersController/updateOrders', qs.stringify({
                        orderId: UsedOrderId.value, 
                        orderTotal: totalPrice.value
                    })).catch(error => {
                        console.error(error);
                    });
                }
                
                router.push({ path: '/payment', query: {
                    orderId: UsedOrderId.value, 
                    discountNum: usePointsNum.value/100,
                    point: point.value}});
            }
            else{
                //新订单
                axios.post('OrdersController/createOrders', qs.stringify({
                userId: user.value.userId,
                businessId: businessId.value,
                daId: deliveryaddress.value.daId,
                orderTotal: totalPrice.value
                })).then(response => {
                    let orderId = response.data;
                    if (orderId > 0) {
                        // router.push({path:'/'});
                        router.push({ path: '/payment', query: {
                            orderId: orderId, 
                            discountNum: usePointsNum.value/100,
                            point: point.value}});
                    } else {
                        alert('创建订单失败!');
                    }
                }).catch(error => {
                    console.error(error);
                });
            }
        }
        
        const totalPrice = computed(() => {
            let totalPrice = 0;
            if(UsedOrderId.value == 0){
                for (let cartItem of cartArr.value) {
                    totalPrice += cartItem.food.foodPrice * cartItem.quantity;
                    //alert(totalPrice);
                }
            }
            else{
                for (let cartItem of cartArr.value) {
                    totalPrice += cartItem.food.foodPrice * cartItem.quantity;
                }
            }
                totalPrice += business.value.deliveryPrice;
                if(usePoints.value){
                    if(point.value/100 <= totalPrice){
                        maxPointsUsing.value = point.value - point.value%100;
                    }
                    else{
                        maxPointsUsing.value = (totalPrice-totalPrice%1)*100;
                    }
                    totalPrice = totalPrice - usePointsNum.value/100;
                }
                //if()//在这个地方要判断是否使用积分
                return totalPrice;
            });

        const sexFilters = (value) => {
            return value === 1 ? '先生' : '女士';
            
        }

        return {
            businessId,
            business,
            user,
            cartArr,
            deliveryaddress,
            usePoints,
            totalPrice,
            usePointsNum,
            maxPointsUsing,
            point,
            UsedOrderId,
            foodArr,
            toPayment,
            toUserAddress,
            toggleUsePoints
        };
    }
}
    

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

/****************** 订单信息部分 ******************/
.wrapper .order-info {
    /*注意这里，不设置高，靠内容撑开。因为地址有可能折行*/
    width: 100%;
    margin-top: 12vw;
    background-color: #0097EF;
    box-sizing: border-box;
    padding: 2vw;
    color: #fff;
}

.wrapper .order-info h5 {
    font-size: 3vw;
    font-weight: 300;
}

.wrapper .order-info .order-info-address {
    width: 100%;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-weight: 700;
    user-select: none;
    cursor: pointer;
    margin: 1vw 0;
}

.wrapper .order-info .order-info-address p {
    width: 90%;
    font-size: 5vw;
}

.wrapper .order-info .order-info-address i {
    font-size: 6vw;
}

.wrapper .order-info p {
    font-size: 3vw;
}

.wrapper h3 {
    box-sizing: border-box;
    padding: 3vw;
    font-size: 4vw;
    color: #666;
    border-bottom: solid 1px #DDD;
}

/****************** 订单明细部分 ******************/
.wrapper .order-detailed {
    width: 100%;
}

.wrapper .order-detailed li {
    width: 100%;
    height: 16vw;
    box-sizing: border-box;
    padding: 3vw;
    color: #666;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.wrapper .order-detailed li .order-detailed-left {
    display: flex;
    align-items: center;
}

.wrapper .order-detailed li .order-detailed-left img {
    width: 10vw;
    height: 10vw;
}

.wrapper .order-detailed li .order-detailed-left p {
    font-size: 3.5vw;
    margin-left: 3vw;
}

.wrapper .order-detailed li p {
    font-size: 3.5vw;
}

.wrapper .order-deliveryfee {
    width: 100%;
    height: 16vw;
    box-sizing: border-box;
    padding: 3vw;
    color: #666;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 3.5vw;
}
.wrapper .order-point {
    width: 100%;
    height: 20vw;
    box-sizing: border-box;
    padding: 3vw;
    color: #666;
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 3.5vw;
}
/****************** 订单合计部分 ******************/
.wrapper .total {
    width: 100%;
    height: 14vw;
    position: fixed;
    left: 0;
    bottom: 0;
    display: flex;
}

.wrapper .total .total-left {
    flex: 2;
    background-color: #505051;
    color: #fff;
    font-size: 4.5vw;
    font-weight: 700;
    user-select: none;
    display: flex;
    justify-content: center;
    align-items: center;
}

.wrapper .total .total-right {
    flex: 1;
    background-color: #38CA73;
    color: #fff;
    font-size: 4.5vw;
    font-weight: 700;
    user-select: none;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
}</style>
