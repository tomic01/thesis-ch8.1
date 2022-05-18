### Auto-generated planning domain from the Institution, Institution Domain and Grounding ###

(Domain Trade)
(Sensor sInstitution)
(ContextVariable cInstitution)
(Actuator pepper01)
(Actuator human01)
(SimpleOperator
(Head cInstitution::Trade)
(RequiredState req1 pepper01::give(sponge))
(RequiredState req2 pepper01::pick(battery))
(RequiredState req3 human01::give(battery))
(RequiredState req4 human01::pick(sponge))
(RequiredState req5 sInstitution::trade_start)
(Constraint Before(req1,req2))
)
