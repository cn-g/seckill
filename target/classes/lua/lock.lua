-- 存活时间
local expire = tonumber(ARGV[2])
local value = tonumber(ARGV[1])
local key = KEYS[1]
-- 加锁
-- local ret = redis.call('set', key, value)
local ret = redis.call("SET", key, value, "NX", "EX", expire)
local strret = tostring(ret)
if strret == 'false' then
    return 0
else
    return 1
end