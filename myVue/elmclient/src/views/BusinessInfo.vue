<template>
    <div class="wrapper">
        <!-- header部分 -->
        <header>
            <p>商家信息</p>
        </header>
        <!-- 商家logo部分 -->
        <div class="business-logo">
            <img :src="business.businessImg">
        </div>
        <!-- 商家信息部分 -->
        <div class="business-info">
            <h1>{{ business.businessName }}</h1>
            <p>&#165;{{ business.starPrice }}起送 &#165;{{ business.deliveryPrice }}配送</p>
            <p>{{ business.businessExplain }}</p>
        </div>
        <!-- 食品列表部分 -->
        <ul class="food">
            <li v-for="(item, index) in foodArr">
                <div class="food-left">
                    <img :src="item.foodImg">
                    <div class="food-left-info">
                        <h3>{{ item.foodName }}</h3>
                        <p>{{ item.foodExplain }}</p>
                        <p>&#165;{{ item.foodPrice }}</p>
                    </div>
                </div>
                <div class="food-right">
                    <div>
                        <i class="fa fa-minus-circle" @click="minus(index)" v-show="item.quantity != 0"></i>
                    </div>
                    <p><span v-show="item.quantity != 0">{{ item.quantity }}</span></p>
                    <div>
                        <i class="fa fa-plus-circle" @click="add(index)"></i>
                    </div>
                </div>
            </li>
        </ul>
        <!-- 购物车部分 -->
        <div class="cart">
            <div class="cart-left">
                <div class="cart-left-icon"
                    :style="totalQuantity == 0 ? 'background-color:#505051, ' : 'background - color:#3190E8; '">
                    <i class="fa fa-shopping-cart"></i>
                    <div class="cart-left-icon-quantity" v-show="totalQuantity != 0">
                        {{ totalQuantity }}</div>
                </div>
                <div class="cart-left-info">
                    <p>&#165;{{ totalPrice }}</p>
                    <p>另需配送费{{ business.deliveryPrice }}元</p>
                </div>
            </div>
            <div class="cart-right">
                <!-- 不够起送费 -->
                <div class="cart-right-item" v-show="totalSettle < business.starPrice"
                    style="background-color: #535356;cursor: default;">
                    &#165;{{ business.starPrice }}起送 </div>
                <!-- 达到起送费 -->
                <div class="cart-right-item" @click="toOrder" v-show="totalSettle >= business.starPrice">
                    去结算
                </div>
            </div>
        </div>
    </div>
</template>


<script>
import { ref, onMounted, inject, computed } from 'vue';

import Footer from '../components/Footer.vue';
import axios from 'axios'; // 添加这一行来引入 axios
import qs from 'qs';
import { useRoute, useRouter } from 'vue-router';

// return {
//             businessId: this.$route.query.businessId,
//             business: {},
//             foodArr: [],
//             user: {}
//         }

export default {
    name: 'BusinessInfo',
    components: {
        Footer
    },
    setup() {
    const $getSessionStorage = inject('$getSessionStorage');
    const route = useRoute();
    const router = useRouter();
    const businessId = ref(route.query.businessId);
    const business = ref({});

    const foodArr = ref([]);
    const user = ref({});
    

    onMounted(() => {
        user.value = $getSessionStorage('user');
        axios.post('BusinessController/getBusinessById', qs.stringify({
            businessId: businessId.value
        })).then(response => {
            business.value = response.data;
        }).catch(error => {
            console.error(error);
        });
        //根据businessId查询所属食品信息 
        axios.post('FoodController/listFoodByBusinessId', qs.stringify({
            businessId: businessId.value
        })).then(response => {
            foodArr.value = response.data;
            for (let i = 0; i < foodArr.value.length; i++) {
                foodArr.value[i].quantity = 0;
            }
            //如果已登录，那么需要去查询购物车中是否已经选购了某个食品 
            if (user.value != null) {
                listCart();
            }
        }).catch(error => {
            console.error(error);
        });

    })

    const listCart = () => {
        axios.post('CartController/listCart', qs.stringify({
            businessId: businessId.value,
            userId: user.value.userId
        })).then(response => {
            const cartArr = response.data; //遍历所有食品列表
            for (let foodItem of foodArr.value) {
                foodItem.quantity = 0;
                for (let cartItem of cartArr) {
                    if (cartItem.foodId == foodItem.foodId) {
                        foodItem.quantity = cartItem.quantity;
                    }
                }
            }
            foodArr.value.sort();
        }).catch(error => {
            console.error(error);
        });
    }

    const add = (index) => {
        //首先做登录验证
        if (user.value == null) {
            router.push({
                path: '/login'
            });
            return;
        }
        if (foodArr.value[index].quantity == 0) { //做insert
            savaCart(index);
        } else {
            //做update
            updateCart(index, 1);
        }
    }

    const minus = (index) => {
        //首先做登录验证
        if (user.value == null) {
            router.push({
                path: '/login'
            });
            return;
        }
        if (foodArr.value[index].quantity > 1) { //做update
            updateCart(index, -1);
        } else {
            //做delete
            removeCart(index);
        }
    }

    const savaCart = (index) => {
        axios.post('CartController/saveCart', qs.stringify({
            businessId: businessId.value,
            userId: user.value.userId,
            foodId: foodArr.value[index].foodId
        })).then(response => {
            if (response.data == 1) {
                //此食品数量要更新为1; 
                foodArr.value[index].quantity = 1; 
                foodArr.value.sort();
            } else {
                alert('向购物车中添加食品失败!');
            }
        }).catch(error => {
            console.error(error);
        });
    }

    const updateCart = (index, num) => {
        axios.post('CartController/updateCart', qs.stringify({
            businessId: businessId.value,
            userId: user.value.userId,
            foodId: foodArr.value[index].foodId,
            quantity: foodArr.value[index].quantity + num
        })).then(response => {
            if (response.data == 1) {
                //此食品数量要更新为1或-1; 
                foodArr.value[index].quantity += num; 
                foodArr.value.sort();
            } else {
                alert('向购物车中更新食品失败!');
            }
        }).catch(error => {
            console.error(error);
        });
    }

    const removeCart = (index) => {
        axios.post('CartController/removeCart', qs.stringify({
            businessId: businessId.value,
            userId: user.value.userId,
            foodId: foodArr.value[index].foodId
        })).then(response => {
            if (response.data == 1) {
                //此食品数量要更新为0;视图的减号和数量要消失 
                foodArr.value[index].quantity = 0; 
                foodArr.value.sort();
            } else {
                alert('从购物车中删除食品失败!');
            }
        }).catch(error => {
            console.error(error);
        });
    }

    const toOrder = () => {
        router.push({
            path: '/orders',
            query: {
                businessId: business.value.businessId
            }
        });
    }

    const totalPrice = computed(() => {
        let total = 0;
        for (let item of foodArr.value) {
            total += item.foodPrice * item.quantity;
        }
        return total;
    });
    //食品总数量 
    const totalQuantity = computed(() => {
        let quantity = 0;
        for (let item of foodArr.value) {
            quantity += item.quantity;
        }
        return quantity;
    });
    //结算总价格 
    const totalSettle = computed(() => {
        return totalPrice.value + business.value.deliveryPrice;
    });

    return {
        businessId,
        business,
        foodArr,
        user,
        listCart,
        add,
        minus,
        savaCart,
        removeCart,
        updateCart,
        toOrder,
        totalPrice,
        totalQuantity,
        totalSettle         
    };
}
}








// import { ref, onMounted } from 'vue';
// import axios from 'axios'; // 添加这一行来引入 axios
// import qs from 'qs';
// import { useRoute, useRouter } from 'vue-router';

// export default {
//     name: 'BusinessInfo',
//     setup() {
//         const route = useRoute();
//         const businessId = ref(route.query.businessId);
//         const business = ref({});
//         const foodArr = ref([]);
//         const user = ref(null);

//         const loadBusinessInfo = async () => {
//             try {
//                 const responseBusiness = await axios.post('BusinessController/getBusinessById', qs.stringify({
//                     businessId: businessId.value
//                 }));
//                 business.value = responseBusiness.data;

//                 const responseFood = await axios.post('FoodController/listFoodByBusinessId', qs.stringify({
//                     businessId: businessId.value
//                 }));
//                 foodArr.value = responseFood.data;
//                 for (let i = 0; i < foodArr.value.length; i++) {
//                     foodArr.value[i].quantity = 0;
//                 }

//                 if (user.value != null) {
//                     loadCart();
//                 }
//             } catch (error) {
//                 console.error(error);
//             }
//         };

//         const loadCart = async () => {
//             try {
//                 const responseCart = await axios.post('CartController/listCart', qs.stringify({
//                     businessId: businessId.value,
//                     userId: user.value.userId
//                 }));
//                 const cartArr = responseCart.data;

//                 for (let foodItem of foodArr.value) {
//                     foodItem.quantity = 0;
//                     for (let cartItem of cartArr) {
//                         if (cartItem.foodId === foodItem.foodId) {
//                             foodItem.quantity = cartItem.quantity;
//                         }
//                     }
//                 }
//                 foodArr.value.sort();
//             } catch (error) {
//                 console.error(error);
//             }
//         };

//         onMounted(() => {
//             user.value = JSON.parse(sessionStorage.getItem('user'));
//             loadBusinessInfo();
//         });

//         // Other methods...

//         return {
//             businessId,
//             business,
//             foodArr,
//             user,
//             // Other data and methods...
//         };
//     },
//     computed: {
//         totalPrice() {
//             let total = 0;
//             for (let item of this.foodArr) {
//                 total += item.foodPrice * item.quantity;
//             }
//             return total;
//         },
//         totalQuantity() {
//             let quantity = 0;
//             for (let item of this.foodArr) {
//                 quantity += item.quantity;
//             }
//             return quantity;
//         },
//         totalSettle() {
//             return this.totalPrice + this.business.deliveryPrice;
//         }
//     }
// };



// // import { useAxios } from '@/composables/useAxios'; // 假设已经创建了一个名为 useAxios 的自定义组合式函数
// import { ref, onMounted, inject, getCurrentInstance } from 'vue';
// import { useRoute, useRouter } from 'vue-router';
// import axios from 'axios'; // 添加这一行来引入 axios
// import qs from 'qs';

// export default {
//     name: 'BusinessInfo',
//     setup() {
//         const route = useRoute();
//         const router = useRouter();
//         // const { post } = useAxios(); // 使用自定义的 useAxios 组合式函数

//         const count = ref(0);
//         const businessId = ref(route.query.businessId);
//         const business = ref({});
//         const foodArr = ref([]);
//         const user = ref(null);

//         const listCart = () => {
//             axios.post('CartController/listCart', qs.stringify({
//                 businessId: businessId.value,
//                 userId: user.value.userId
//             })).then(response => {
//                 let cartArr = response.data;
//                 for (let foodItem of foodArr.value) {
//                     foodItem.quantity = 0;
//                     for (let cartItem of cartArr) {
//                         if (cartItem.foodId == foodItem.foodId) {
//                             foodItem.quantity = cartItem.quantity;
//                         }
//                     }
//                 }
//                 foodArr.value.sort();
//             }).catch(error => {
//                 console.error(error);
//             });
//         };

//         const add = (index) => {
//             if (user.value == null) {
//                 router.push({
//                     path: '/login'
//                 });
//                 return;
//             }
//             if (foodArr.value[index].quantity == 0) {
//                 saveCart(index);
//             } else {
//                 updateCart(index, 1);
//             }
//         };

//         const minus = (index) => {
//             if (user.value == null) {
//                 router.push({
//                     path: '/login'
//                 });
//                 return;
//             }
//             if (foodArr.value[index].quantity > 1) {
//                 updateCart(index, -1);
//             } else {
//                 removeCart(index);
//             }
//         };

//         const saveCart = (index) => {
//             axios.post('CartController/saveCart', qs.stringify({
//                 businessId: businessId.value,
//                 userId: user.value.userId,
//                 foodId: foodArr.value[index].foodId
//             })).then(response => {
//                 if (response.data == 1) {
//                     foodArr.value[index].quantity = 1;
//                     foodArr.value.sort();
//                 } else {
//                     alert('向购物车中添加食品失败!');
//                 }
//             }).catch(error => {
//                 console.error(error);
//             });
//         };

//         const updateCart = (index, num) => {
//             axios.post('CartController/updateCart', qs.stringify({
//                 businessId: businessId.value,
//                 userId: user.value.userId,
//                 foodId: foodArr.value[index].foodId,
//                 quantity: foodArr.value[index].quantity + num
//             })).then(response => {
//                 if (response.data == 1) {
//                     foodArr.value[index].quantity += num;
//                     foodArr.value.sort();
//                 } else {
//                     alert('向购物车中更新食品失败!');
//                 }
//             }).catch(error => {
//                 console.error(error);
//             });
//         };

//         const removeCart = (index) => {
//             axios.post('CartController/removeCart', qs.stringify({
//                 businessId: businessId.value,
//                 userId: user.value.userId,
//                 foodId: foodArr.value[index].foodId
//             })).then(response => {
//                 if (response.data == 1) {
//                     foodArr.value[index].quantity = 0;
//                     foodArr.value.sort();
//                 } else {
//                     alert('从购物车中删除食品失败!');
//                 }
//             }).catch(error => {
//                 console.error(error);
//             });
//         };

//         const toOrder = () => {
//             router.push({
//                 path: '/orders',
//                 query: {
//                     businessId: business.value.businessId
//                 }
//             });
//         };

//         const totalPrice = computed(() => {
//             let total = 0;
//             for (let item of foodArr.value) {
//                 total += item.foodPrice * item.quantity;
//             }
//             return total;
//         });

//         const totalQuantity = computed(() => {
//             let quantity = 0;
//             for (let item of foodArr.value) {
//                 quantity += item.quantity;
//             }
//             return quantity;
//         });

//         const totalSettle = computed(() => {
//             return totalPrice.value + business.value.deliveryPrice;
//         });

//         // 在需要的地方调用 listCart 函数以初始化数据

//         return {
//             count,
//             businessId,
//             business,
//             foodArr,
//             user,
//             listCart,
//             add,
//             minus,
//             saveCart,
//             updateCart,
//             removeCart,
//             toOrder,
//             totalPrice,
//             totalQuantity,
//             totalSettle,
//         };
//     },
// };
// ///



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

/****************** 商家logo部分 ******************/
.wrapper .business-logo {
    width: 100%;
    height: 35vw;
    /*使用上外边距避开header部分*/
    margin-top: 12vw;
    display: flex;
    justify-content: center;
    align-items: center;
}

.wrapper .business-logo img {
    width: 40vw;
    height: 30vw;
    border-radius: 5px;
}

/****************** 商家信息部分 ******************/
.wrapper .business-info {
    width: 100%;
    height: 20vw;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
}

.wrapper .business-info h1 {
    font-size: 5vw;
}

.wrapper .business-info p {
    font-size: 3vw;
    color: #666;
    margin-top: 1vw;
}

/****************** 食品列表部分 ******************/
.wrapper .food {
    width: 100%;
    /*使用下外边距避开footer部分*/
    margin-bottom: 14vw;
}

.wrapper .food li {
    width: 100%;
    box-sizing: border-box;
    padding: 2.5vw;
    user-select: none;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.wrapper .food li .food-left {
    display: flex;
    align-items: center;
}

.wrapper .food li .food-left img {
    width: 20vw;
    height: 20vw;
}

.wrapper .food li .food-left .food-left-info {
    margin-left: 3vw;
}

.wrapper .food li .food-left .food-left-info h3 {
    font-size: 3.8vw;
    color: #555;
}

.wrapper .food li .food-left .food-left-info p {
    font-size: 3vw;
    color: #888;
    margin-top: 2vw;
}

.wrapper .food li .food-right {
    width: 16vw;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.wrapper .food li .food-right .fa-minus-circle {
    font-size: 5.5vw;
    color: #999;
    cursor: pointer;
}

.wrapper .food li .food-right p {
    font-size: 3.6vw;
    color: #333;
}

.wrapper .food li .food-right .fa-plus-circle {
    font-size: 5.5vw;
    color: #0097EF;
    cursor: pointer;
}

/****************** 购物车部分 ******************/
.wrapper .cart {
    width: 100%;
    height: 14vw;
    position: fixed;
    left: 0;
    bottom: 0;
    display: flex;
}

.wrapper .cart .cart-left {
    flex: 2;
    background-color: #505051;
    display: flex;
}

.wrapper .cart .cart-left .cart-left-icon {
    width: 16vw;
    height: 16vw;
    box-sizing: border-box;
    border: solid 1.6vw #444;
    border-radius: 8vw;
    background-color: #3190E8;
    font-size: 7vw;
    color: #fff;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: -4vw;
    margin-left: 3vw;
    position: relative;
}

.wrapper .cart .cart-left .cart-left-icon-quantity {
    width: 5vw;
    height: 5vw;
    border-radius: 2.5vw;
    background-color: red;
    color: #fff;
    font-size: 3.6vw;
    display: flex;
    justify-content: center;
    align-items: center;
    position: absolute;
    right: -1.5vw;
    top: -1.5vw;
}

.wrapper .cart .cart-left .cart-left-info p:first-child {
    font-size: 4.5vw;
    color: #fff;
    margin-top: 1vw;
}

.wrapper .cart .cart-left .cart-left-info p:last-child {
    font-size: 2.8vw;
    color: #AAA;
}

.wrapper .cart .cart-right {
    flex: 1;
}

/*达到起送费时的样式*/
.wrapper .cart .cart-right .cart-right-item {
    width: 100%;
    height: 100%;
    background-color: #38CA73;
    color: #fff;
    font-size: 4.5vw;
    font-weight: 700;
    user-select: none;
    cursor: pointer;
    display: flex;
    justify-content: center;
    align-items: center;
}

/*不够起送费时的样式(只有背景色和鼠标样式的区别)*/
/*
.wrapper .cart .cart-right .cart-right-item{
        width: 100%;
        height: 100%;
        background-color: #535356;
        color: #fff;
        font-size: 4.5vw;
        font-weight: 700;
        user-select: none;
        display: flex;
        justify-content: center;
        align-items: center;
}
*/
</style>