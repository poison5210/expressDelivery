<template>
  <div class="dashboard-container">
    <section class="hero">
      <div class="hero-content">
        <div class="hero-title">驱动物流效率革新，重塑末端配送体验。</div>
        <div class="hero-subtitle">我们致力于为客户提供物流解决方案，整合优质资源，为您带来更高效、更可靠的服务。</div>
      </div>
    </section>
    <div class="container">

      <!-- 顶部标题（跨两列） -->
      <div class="header">
        <h1>智能化收寄快递系统</h1>
        <div class="order-nav">
          <button class="active">订单摘要</button>
          <button>订单统计</button>
        </div>
        <div class="order-stats">
          <div class="stat">
            <h3>当天总收入</h3>
            <div class="number">15W</div>
          </div>
          <div class="stat">
            <h3>当天订单</h3>
            <div class="number">1759</div>
          </div>
          <div class="stat">
            <h3>累计订单</h3>
            <div class="number">234</div>
          </div>
        </div>
      </div>

      <!-- 左侧主内容：包含图表、产品销量、产品创意、优惠活动等 -->
      <div class="main-content">
        <!-- 订单或产品详情图表区 -->
        <div class="chart-section">
          <h2>订单统计图表</h2>
          <div class="chart-placeholder">图表区域（可用图表库替换）</div>
        </div>

        <!-- 产品销量 -->
        <div class="product-sales">
          <h2>产品销量</h2>
          <div class="sales-chart-placeholder">销量图表（可用图表库替换）</div>

          <!-- 产品创意 -->
          <div class="product-ideas">
            <div class="idea-item">
              <img src="https://via.placeholder.com/40" alt="icon" />
              <div>创意1</div>
            </div>
            <div class="idea-item">
              <img src="https://via.placeholder.com/40" alt="icon" />
              <div>创意2</div>
            </div>
            <div class="idea-item">
              <img src="https://via.placeholder.com/40" alt="icon" />
              <div>创意3</div>
            </div>
            <div class="idea-item">
              <img src="https://via.placeholder.com/40" alt="icon" />
              <div>创意4</div>
            </div>
            <div class="idea-item">
              <img src="https://via.placeholder.com/40" alt="icon" />
              <div>创意5</div>
            </div>
          </div>
        </div>

        <!-- 优惠活动&业务 -->
        <div class="promotions">
          <h2>优惠活动 &amp; 业务</h2>
          <ul>
            <li>活动1</li>
            <li>活动2</li>
            <li>活动3</li>
            <li>活动4</li>
          </ul>
        </div>
      </div>

      <!-- 右侧客户评价 -->
      <div class="customer-review">
        <h2>客户评价</h2>
        <div class="review-list">
          <div class="review-item">
            <div class="review-content">
              <div class="name">无名氏</div>
              <div class="comment">非常满意，发货速度快</div>
            </div>
          </div>
          <div class="review-item">
            <div class="review-content">
              <div class="name">天宇</div>
              <div class="comment">满意</div>
            </div>
          </div>
          <div class="review-item">
            <div class="review-content">
              <div class="name">王五</div>
              <div class="comment">客服态度很好</div>
            </div>
          </div>
        </div>
      </div>

      <!-- 底部 Footer（跨两列） -->
      <div class="footer">
        <p>© 智能化收寄快递系统</p>
      </div>

    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters([
      'name'
    ])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
<style lang="scss" scoped>
/* 顶部Banner区域 */
.hero {
  position: relative;
  width: 100%;
  display: flex;
  align-items: center;
  /* 示例：右侧背景图 */
  margin-left: 25px;
  background-repeat: no-repeat;
  background-position: right center;
  background-size: contain;
}
.hero-title {
  font-size: 32px;
  font-weight: bold;
  margin-bottom: 16px;
  line-height: 1.4;
  color: #000;
}
.hero-subtitle {
  font-size: 18px;
  color: #666;
  line-height: 1.6;
}

/* 响应式示例，可根据实际需求调整 */
@media (max-width: 768px) {
  .hero {
    height: auto;
    padding: 40px 20px;
    background-size: cover; /* 或 cover/contain 根据需要 */
  }
  .hero-title {
    font-size: 24px;
  }
  .features-search {
    flex-direction: column;
    gap: 20px;
  }
  .features {
    justify-content: center;
  }
}
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, sans-serif;
  background-color: #f0f0f0;
  color: #333;
}

/* 容器整体布局：左侧主内容 + 右侧客户评价栏 */
.container {
  display: grid;
  grid-template-columns: 1fr 320px; /* 左侧自适应宽度 + 右侧固定宽度 */
  gap: 20px;
  padding: 20px;
  max-width: 1600px;
  margin: 0 auto;
}

/* 顶部大标题（占据左侧和右侧） */
.header {
  grid-column: 1 / 3;
  background-color: #2c2c2c;
  color: #fff;
  padding: 20px;
  border-radius: 8px;
}

.header h1 {
  font-size: 24px;
  font-weight: bold;
}

/* 顶部的“订单摘要 / 订单统计”导航区 */
.order-nav {
  display: flex;
  margin-top: 20px;
  gap: 16px;
}
.order-nav button {
  background-color: #4a4a4a;
  color: #fff;
  border: none;
  border-radius: 4px;
  padding: 8px 16px;
  cursor: pointer;
}
.order-nav button.active {
  background-color: #e74c3c;
}

/* 订单统计概览：当天总收入、当天订单、累计订单 */
.order-stats {
  display: flex;
  margin-top: 20px;
  gap: 40px;
}
.order-stats .stat {
  display: flex;
  flex-direction: column;
  align-items: center;
}
.order-stats .stat h3 {
  font-size: 18px;
  margin-bottom: 8px;
}
.order-stats .stat .number {
  font-size: 20px;
  font-weight: bold;
}

/* 中间内容：订单统计图表 + 产品销量图表等 */
.main-content {
  display: flex;
  flex-direction: column;
  gap: 20px;
}

/* 产品详情图表区 */
.chart-section {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
}
.chart-section h2 {
  font-size: 18px;
  margin-bottom: 16px;
}
.chart-placeholder {
  width: 100%;
  height: 200px;
  background-color: #ececec;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 14px;
}

/* 产品销量、产品数据 */
.product-sales {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
}
.product-sales h2 {
  font-size: 18px;
  margin-bottom: 16px;
}
.sales-chart-placeholder {
  width: 100%;
  height: 150px;
  background-color: #ececec;
  border-radius: 4px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #999;
  font-size: 14px;
}

/* 产品创意区（图标列表） */
.product-ideas {
  margin-top: 20px;
  display: flex;
  gap: 16px;
  flex-wrap: wrap;
}
.idea-item {
  flex: 0 0 100px;
  background-color: #f9f9f9;
  border-radius: 8px;
  text-align: center;
  padding: 10px;
}
.idea-item img {
  width: 40px;
  height: 40px;
  object-fit: contain;
  margin-bottom: 8px;
}

/* 优惠活动&业务 */
.promotions {
  margin-top: 20px;
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
}
.promotions h2 {
  font-size: 18px;
  margin-bottom: 16px;
}
.promotions ul {
  list-style: none;
  display: flex;
  flex-wrap: wrap;
  gap: 12px;
}
.promotions li {
  background-color: #f9f9f9;
  border-radius: 4px;
  padding: 8px 12px;
}

/* 右侧：客户评价 */
.customer-review {
  background-color: #fff;
  border-radius: 8px;
  padding: 20px;
  height: fit-content; /* 内容自适应高度 */
}
.customer-review h2 {
  font-size: 18px;
  margin-bottom: 16px;
}
.review-list {
  display: flex;
  flex-direction: column;
  gap: 16px;
}
.review-item {
  display: flex;
  align-items: center;
  gap: 10px;
}
.review-item img {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  object-fit: cover;
}
.review-content {
  display: flex;
  flex-direction: column;
}
.review-content .name {
  font-weight: bold;
}
.review-content .comment {
  font-size: 14px;
  color: #666;
}

/* 底部 Footer */
.footer {
  grid-column: 1 / 3;
  background-color: #2c2c2c;
  color: #fff;
  border-radius: 8px;
  padding: 20px;
  margin-top: 20px;
  text-align: center;
}
</style>
