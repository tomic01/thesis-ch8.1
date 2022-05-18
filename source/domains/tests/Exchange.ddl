### Auto-generated planning domain from the Institution, Institution Domain and Grounding ###

(Domain Exchange)
(Sensor sInstitution)
(ContextVariable cInstitution)
(Actuator pepper01)
(Actuator pepper02)
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
