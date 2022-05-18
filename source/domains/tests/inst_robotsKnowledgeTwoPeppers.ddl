
(Domain Institution)


###### BEGIN: 	institution knowledge ######
(Sensor sInstitution)
(ContextVariable cInstitution)
(SimpleOperator
(Head cInstitution::Exchange)
(RequiredState req1 pepper01::give(sponge))
(RequiredState req2 pepper01::pickUp(battery))
(RequiredState req3 pepper02::give(battery))
(RequiredState req4 pepper02::pickUp(sponge))
(RequiredState req5 sInstitution::exchange_start)
(Constraint Overlaps(req1,req4))
(Constraint Overlaps(req3,req2))
)
###### END: 	institution knowledge ######

(Actuator pepper01)
(Actuator pepper02)
(Actuator used)

(Resource rPepper01 1)
(Resource rPepper02 1)

(SimpleOperator
(Head pepper01::give(sponge))
(RequiredState req1 pepper01::pickUp(sponge))
(RequiredState req2 used::pepper01())
(Constraint MetByOrAfter(Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[60000,INF](Head))
)

(SimpleOperator
(Head pepper01::pickUp(sponge))
(RequiredState req1 pepper01::leaveObject(battery))
(RequiredState req2 used::pepper01())
(Constraint MetByOrAfter[1,1](Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[45000,INF](Head))
)

(SimpleOperator
(Head pepper01::locate(sponge))
(Constraint Duration[1000,INF](Head))
)

(SimpleOperator
(Head pepper01::leaveObject(sponge))
(RequiredState req1 used::pepper01())
(Constraint Equals(Head,req1))
(Constraint Duration[3000,45000](Head))
)


(SimpleOperator
(Head pepper01::give(battery))
(RequiredState req1 pepper01::pickUp(battery))
(RequiredState req2 used::pepper01())
(Constraint MetByOrAfter(Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[60000,INF](Head))
)

(SimpleOperator
(Head pepper01::pickUp(battery))
(RequiredState req1 pepper01::leaveObject(sponge))
(RequiredState req2 used::pepper01())
(Constraint MetByOrAfter[1,1](Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[45000,INF](Head))
)

(SimpleOperator
(Head pepper01::locate(battery))
(Constraint Duration[1000,INF](Head))
)

(SimpleOperator
(Head pepper01::leaveObject(battery))
(RequiredState req1 used::pepper01())
(Constraint Equals(Head,req1))
(Constraint Duration[3000,45000](Head))
)



(SimpleOperator
(Head pepper02::give(sponge))
(RequiredState req1 pepper02::pickUp(sponge))
(RequiredState req2 used::pepper02())
(Constraint MetByOrAfter(Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[60000,INF](Head))
)

(SimpleOperator
(Head pepper02::pickUp(sponge))
(RequiredState req1 pepper02::leaveObject(battery))
(RequiredState req2 used::pepper02())
(Constraint MetByOrAfter[1,1](Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[45000,INF](Head))
)

(SimpleOperator
(Head pepper02::locate(sponge))
(Constraint Duration[1000,INF](Head))
)

(SimpleOperator
(Head pepper02::leaveObject(sponge))
(RequiredState req1 used::pepper02())
(Constraint Equals(Head,req1))
(Constraint Duration[3000,45000](Head))
)


(SimpleOperator
(Head pepper02::give(battery))
(RequiredState req1 pepper02::pickUp(battery))
(RequiredState req2 used::pepper02())
(Constraint MetByOrAfter(Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[60000,INF](Head))
)

(SimpleOperator
(Head pepper02::pickUp(battery))
(RequiredState req1 pepper02::leaveObject(sponge))
(RequiredState req2 used::pepper02())
(Constraint MetByOrAfter[1,1](Head,req1))
(Constraint Equals(Head,req2))
(Constraint Duration[45000,INF](Head))
)

(SimpleOperator
(Head pepper02::locate(battery))
(Constraint Duration[1000,INF](Head))
)

(SimpleOperator
(Head pepper02::leaveObject(battery))
(RequiredState req1 used::pepper02())
(Constraint Equals(Head,req1))
(Constraint Duration[3000,45000](Head))
)


(SimpleOperator
(Head used::pepper01())
(RequiredResource rPepper01(1))
)

(SimpleOperator
(Head used::pepper02())
(RequiredResource rPepper02(1))
)
