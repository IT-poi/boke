# boke
一个分布式个人博客系统

后端采用springboot+springcloud实现，使用elasticsearch实现文章全文检索，jwt+redis黑名单实现登出登入功能，zuul做权限认证、请求转发及统一异常处理

前段采用angularJs+bootstrap，基于网上的模板进行开发，响应式布局，分为博客展示端，和博客后台管理页面

前后端完全分离，前端采用nginx的方式部署
