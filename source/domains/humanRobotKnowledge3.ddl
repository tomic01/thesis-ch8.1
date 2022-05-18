### The real behavior times

(Domain Institution)

(Actuator pepper01)
(Actuator human01)
(Actuator used)

(Resource rPepper01 1)
(Resource rHuman01 1)

# Pepper01-Sponge #

(SimpleOperator
 (Head pepper01::give(sponge))
 (RequiredState req1 pepper01::pick(sponge))
 (RequiredState req2 used::pepper01())
 (RequiredState req3 human01::pick(sponge))
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
 (RequiredState req3 human01::pick(battery))
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

######### Human #############

# human01-Sponge #

(SimpleOperator
 (Head human01::give(sponge))
 (RequiredState req2 used::human01())
 (RequiredState req3 pepper01::pick(sponge))
 (Constraint Equals(Head,req2))
 (Constraint Overlaps(Head,req3))
 (Constraint Duration[60000,INF](Head))
)

# human01-Battery #

(SimpleOperator
 (Head human01::give(battery))
 (RequiredState req2 used::human01())
 (RequiredState req3 pepper01::pick(battery))
 (Constraint Equals(Head,req2))
 (Constraint Overlaps(Head,req3))
 (Constraint Duration[60000,INF](Head))
)

### Used Robots

(SimpleOperator
 (Head used::pepper01())
 (RequiredResource rPepper01(1))
)

(SimpleOperator
 (Head used::human01())
 (RequiredResource rHuman01(1))
)
