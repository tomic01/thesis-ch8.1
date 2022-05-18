### The real behavior times

(Domain Institution)

#(Sensor sPepper01Has)
#(Sensor sPepper02Has)

(Actuator pepper01)
(Actuator pepper02)
(Actuator used)

(Resource rPepper01 1)
(Resource rPepper02 1)

# Pepper01-Sponge #

(SimpleOperator
 (Head pepper01::give(sponge))
 (RequiredState req1 pepper01::pick(sponge))
 (RequiredState req2 used::pepper01())
 (RequiredState req3 pepper02::pick(sponge))
 (Constraint MetByOrAfter[1,4000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Overlaps(Head,req3))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper01::pick(sponge))
 (RequiredState req1 pepper01::leaveObject(battery))
 (RequiredState req2 used::pepper01())
 (Constraint MetByOrAfter[1,1](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper01::locate(sponge))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper01::leaveObject(sponge))
 (RequiredState req1 used::pepper01())
 (Constraint Equals(Head,req1))
 (Constraint Duration[5000,20000](Head))
)

# Pepper01-Battery #

(SimpleOperator
 (Head pepper01::give(battery))
 (RequiredState req1 pepper01::pick(battery))
 (RequiredState req2 used::pepper01())
 (RequiredState req3 pepper02::pick(battery))
 (Constraint MetByOrAfter[1,4000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Overlaps(Head,req3))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper01::pick(battery))
 (RequiredState req1 pepper01::leaveObject(sponge))
 (RequiredState req2 used::pepper01())
 (Constraint MetByOrAfter[1,1](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper01::locate(battery))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper01::leaveObject(battery))
 (RequiredState req1 used::pepper01())
 (Constraint Equals(Head,req1))
 (Constraint Duration[5000,20000](Head))
)

######### PeEpper02 #############

# Pepper02-Sponge #

(SimpleOperator
 (Head pepper02::give(sponge))
 (RequiredState req1 pepper02::pick(sponge))
 (RequiredState req2 used::pepper02())
 (RequiredState req3 pepper01::pick(sponge))
 (Constraint MetByOrAfter[1,4000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Overlaps(Head,req3))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper02::pick(sponge))
 (RequiredState req1 pepper02::leaveObject(battery))
 (RequiredState req2 used::pepper02())
 (Constraint MetByOrAfter[1,1](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper02::locate(sponge))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper02::leaveObject(sponge))
 (RequiredState req1 used::pepper02())
 (Constraint Equals(Head,req1))
 (Constraint Duration[5000,20000](Head))
)

# Pepper02-Battery #

(SimpleOperator
 (Head pepper02::give(battery))
 (RequiredState req1 pepper02::pick(battery))
 (RequiredState req2 used::pepper02())
 (RequiredState req3 pepper01::pick(battery))
 (Constraint MetByOrAfter[1,4000](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Overlaps(Head,req3))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper02::pick(battery))
 (RequiredState req1 pepper02::leaveObject(sponge))
 (RequiredState req2 used::pepper02())
 (Constraint MetByOrAfter[1,1](Head,req1))
 (Constraint Equals(Head,req2))
 (Constraint Duration[60000,INF](Head))
)

(SimpleOperator
 (Head pepper02::locate(battery))
 (Constraint Duration[1000,INF](Head))
)

(SimpleOperator
 (Head pepper02::leaveObject(battery))
 (RequiredState req1 used::pepper02())
 (Constraint Equals(Head,req1))
 (Constraint Duration[5000,20000](Head))
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
