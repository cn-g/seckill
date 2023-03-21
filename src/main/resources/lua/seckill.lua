--商品id
local goodsId = tostring(ARGV[1])
--用户id
local userId = ARGV[2]
--购买数量
local num = ARGV[3]
--商品库存
local stock = "cache_seckill_goods:"..goodsId
--商品订单列表
local order = "cache_seckill:order:"..goodsId
-- 查询商品库存
local stockNum = redis.call('get',stock)
if tonumber(stockNum) ~= nil and tonumber(stockNum) >= tonumber(num) then
    -- 判断用户是否下过单
    if redis.call('sismember',order,tonumber(userId)) == 0 then
        -- 扣除库存
        redis.call('incrby',stock,-tonumber(num))
        redis.call('sadd',order,tonumber(userId))
        return 1 -- 下单成功
    else
        return 0 -- 下单失败：用户不可重复下单
    end
else
    return -1 -- 库存不足
end

