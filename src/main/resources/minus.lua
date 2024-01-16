local count = tonumber(redis.call("get",KEYS[1]));
if count > 0 and count >= 1 then
    redis.call("decr",KEYS[1])
end