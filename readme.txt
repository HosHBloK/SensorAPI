For sensor registration: "/auth/registration/sensor"
{
	"username" : " your sensor name ",
	"password" : " your sensor password "
}

-------------------------------------------------------------
For person registration: "/auth/registration/person"
{
	"username" : " your name ",
	"password" : " your password "
}

-------------------------------------------------------------
To refresh your token (expires after 6 hours): /auth/refresh_token

{
	"username" : " your username ",
	"password" : " your password ",
	"role" : " your role - 'sensor' or 'person' "
}

-------------------------------------------------------------
To add a measurement: "/measurements/add" with header "Authorization" with value: "Bearer 'Sensor JWT token'"

{
	"value" : " float value ",
	"raining" : " boolean value ",
	"username" : " name of current sensor "
}

-------------------------------------------------------------
To get measurements of some sensor: "/measurements/get" with header "Authorization" with value: "Bearer 'Person JWT token'"

{
	"username" : " the name of the sensor you want to get measurements from "
}