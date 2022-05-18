### Two robots cooperating
# pepper01 can give(Sponge), give(Battery) and pickUp(Sponge), pickUp(Battery);
# Simplified leave object

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
 (Constraint Equals(Head,req2))
 (Constraint Duration[40000,INF](Head))
)

(SimpleOperator
 (Head pepper01::pickUp(sponge))
 (RequiredState req1 pepper01::leaveObject(battery))
 (RequiredState req2 used::pepper01())
 (Constraint After[1000,40000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper01::locate(sponge))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper01::leaveObject(sponge))
 (RequiredState req1 used::pepper01())
 (Constraint Equals(Head,req1))
 (Constraint Duration[3000,INF](Head))
)

# Pepper01-Battery #

(SimpleOperator
 (Head pepper01::give(battery))
 (RequiredState req1 pepper01::pickUp(battery))
 (RequiredState req2 used::pepper01())
 (Constraint MetBy(Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[45000,INF](Head))
)

(SimpleOperator
 (Head pepper01::pickUp(battery))
 (RequiredState req1 pepper01::leaveObject(sponge))
 (RequiredState req2 used::pepper01())
 (Constraint After[1000,40000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper01::locate(battery))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper01::leaveObject(battery))
 (RequiredState req1 used::pepper01())
 (Constraint Equals(Head,req1))
 (Constraint Duration[3000,INF](Head))
)

######### PeEpper02 #############

# Pepper02-Sponge #

(SimpleOperator
 (Head pepper02::give(sponge))
 (RequiredState req1 pepper02::pickUp(sponge))
 (RequiredState req2 used::pepper02())
 (Constraint MetBy(Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[45000,INF](Head))
)

(SimpleOperator
 (Head pepper02::pickUp(sponge))
 (RequiredState req1 pepper02::leaveObject(battery))
 (RequiredState req2 used::pepper02())
 (Constraint After[1000,40000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper02::locate(sponge))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper02::leaveObject(sponge))
 (RequiredState req1 used::pepper02())
 (Constraint Equals(Head,req1))
 (Constraint Duration[3000,INF](Head))
)

# Pepper02-Battery #

(SimpleOperator
 (Head pepper02::give(battery))
 (RequiredState req1 pepper02::pickUp(battery))
 (RequiredState req2 used::pepper02())
 (Constraint MetBy(Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[45000,INF](Head))
)

(SimpleOperator
 (Head pepper02::pickUp(battery))
 (RequiredState req1 pepper02::leaveObject(sponge))
 (RequiredState req2 used::pepper02())
 (Constraint After[1000,40000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[25000,INF](Head))
)

(SimpleOperator
 (Head pepper02::locate(battery))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper02::leaveObject(battery))
 (RequiredState req1 used::pepper02())
 (Constraint Equals(Head,req1))
 (Constraint Duration[3000,INF](Head))
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
