
------- this could be advanced config with some cute things: -------

local luckyDayStr, todayStr = "Fri", os.date()
-- apply whole section conditionally
if todayStr:find(luckyDayStr) then
	Laser {
		damage = 1000, -- isn't this cool weapon
		durability = 10,
		velocity = -30 * math.pi -- we can do some calculations
	}
	-- arbitrary standart library functions can be called,
	-- though acces to some or all of them can be prevented e.g. for security reasons
	print("Yeeejjj!!! Your are lucky! You'll have laser-on-steroids!")
else
	Laser {
		damage = 10,
		durability = 10,
		velocity = -800
	}
end

-- instead of swapping whole 'sections' like above we can inline condition:
-- damage = todayStr:find(luckyDayStr) and 1000 or 10,





------- old boring config style is supported too: -------

Shotgun {
	damage = 10,
	durability = 180,
	velocity = -150
}

Fireball {
	damage = 100,
	durability = 900,
	velocity = -180
}

Rocket {
	damage = 35,
	durability = 50,
	velocity = -100
}








