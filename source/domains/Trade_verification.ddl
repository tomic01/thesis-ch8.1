### Auto-generated VERIFICATION domain ###

(Domain Trade_Adherence)


### Sensors (state-variables) ###
(Sensor active_human01_give)
(Sensor active_human01_pick)
(Sensor active_human02_give)
(Sensor active_human02_pick)


### Context variables (norms) ###
(ContextVariable cNorm1)
(ContextVariable cNorm2)
(ContextVariable cNorm3)
(ContextVariable cNorm4)
(ContextVariable cNorm5)


### Actuators (shows norms in the timeline) ###
(Actuator aNorm1)
(Actuator aNorm2)
(Actuator aNorm3)
(Actuator aNorm4)
(Actuator aNorm5)


### Norms context operators ###

(SimpleOperator
(Head cNorm1::must(human01,give,yellowsponge))
(RequiredState req1 active_human01_give::yellowsponge)
(RequiredState req2 aNorm1::must(human01,give,yellowsponge))
(Constraint StartedBy(Head,req1))
(Constraint Duration[5000,20000](req2))
)


(SimpleOperator
(Head cNorm2::must(human01,pick,bluesponge))
(RequiredState req1 active_human01_pick::bluesponge)
(RequiredState req2 aNorm2::must(human01,pick,bluesponge))
(Constraint StartedBy(Head,req1))
(Constraint Duration[5000,20000](req2))
)


(SimpleOperator
(Head cNorm3::must(human02,give,bluesponge))
(RequiredState req1 active_human02_give::bluesponge)
(RequiredState req2 aNorm3::must(human02,give,bluesponge))
(Constraint StartedBy(Head,req1))
(Constraint Duration[5000,20000](req2))
)


(SimpleOperator
(Head cNorm4::must(human02,pick,yellowsponge))
(RequiredState req1 active_human02_pick::yellowsponge)
(RequiredState req2 aNorm4::must(human02,pick,yellowsponge))
(Constraint StartedBy(Head,req1))
(Constraint Duration[5000,20000](req2))
)


(SimpleOperator
(Head cNorm5::before)
(RequiredState req1 active_human01_give::yellowsponge)
(RequiredState req2 active_human01_pick::bluesponge)
(RequiredState req3 aNorm5::before)
(Constraint Before(req1,req2))
(Constraint Duration[5000,20000](req3))
)


