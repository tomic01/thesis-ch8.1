### Idea, use sensors to determine if we want to dispatch leaveObject with duration or wihout duration

(Domain Institution)

(Sensor sPepper01Has)
(Sensor sPepper02Has)

(Actuator pepper01)
(Actuator pepper02)
(Actuator used)

(Resource rPepper01 1)
(Resource rPepper02 1)

# Pepper01-Sponge #

(SimpleOperator
 (Head pepper01::give(sponge))
 (RequiredState req1 pepper01::pickUp(sponge))
 (RequiredState req2 used::pepper01())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[40000,INF](Head))
)

(SimpleOperator
 (Head pepper01::pickUp(sponge))
 (RequiredState req1 pepper01::leaveObject())
 (RequiredState req2 used::pepper01())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper01::locate(sponge))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper01::leaveObject())
 (RequiredState req1 sPepper01Has::none)
)

(SimpleOperator
 (Head pepper01::leaveObject())
 (RequiredState req1 used::pepper01())
 (RequiredState req2 cPepper01Has::SomeObject)
 (Constraint During(Head,req1))
 (Constraint Duration[25000,INF](Head))
)

# Pepper01-Battery #

(SimpleOperator
 (Head pepper01::give(battery))
 (RequiredState req1 pepper01::pickUp(battery))
 (RequiredState req2 used::pepper01())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[45000,INF](Head))
)

(SimpleOperator
 (Head pepper01::pickUp(battery))
 (RequiredState req1 pepper01::leaveObject())
 (RequiredState req2 used::pepper01())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper01::locate(battery))
 (Constraint Duration[1000,INF](Head))
)

######### PeEpper02 #############

# Pepper02-Sponge #

(SimpleOperator
 (Head pepper02::give(sponge))
 (RequiredState req1 pepper02::pickUp(sponge))
 (RequiredState req2 used::pepper02())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[45000,INF](Head))
)

(SimpleOperator
 (Head pepper02::pickUp(sponge))
 (RequiredState req1 pepper02::leaveObject())
 (RequiredState req2 used::pepper02())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper02::locate(sponge))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper02::leaveObject())
 (RequiredState req1 cPepper02Has::Nothing)
)

(SimpleOperator
 (Head pepper02::leaveObject())
 (RequiredState req1 used::pepper02())
 (RequiredState req2 cPepper02Has::SomeObject)
 (Constraint During(Head,req1))
 (Constraint Duration[25000,INF](Head))
)

# Pepper02-Battery #

(SimpleOperator
 (Head pepper02::give(battery))
 (RequiredState req1 pepper02::pickUp(battery))
 (RequiredState req2 used::pepper02())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[45000,INF](Head))
)

(SimpleOperator
 (Head pepper02::pickUp(battery))
 (RequiredState req1 pepper02::leaveObject())
 (RequiredState req2 used::pepper02())
 (Constraint MetBy(Head,req1))
 (Constraint During(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper02::locate(battery))
 (Constraint Duration[1000,INF](Head))
)


### Used Robots

(SimpleOperator
 (Head used::pepper01())
 (RequiredResource rPepper01(1))
)

(SimpleOperator
 (Head used::pepper02())
 (RequiredResource rPepper02(1))
)
