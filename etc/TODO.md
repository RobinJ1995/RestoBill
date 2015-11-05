# To do

* "Admin" area where tables and products can be managed
* Just number tables (perhaps even change the `String name` field to `short number`)
	* Add Table dialog would simply become a confirmation dialog
* First-run wizard where the app can either be connected to a master server (see stretch goal #1) or the number of tables can be chosen

# Stretch goals

* Synchronisation with multiple devices via a master server (probably best to write in PHP)
	* App needs to keep functioning if waiter walks out of range of wifi. When connection is restored synchronisation can continue.
	* The way to do this is probably to extend SugarRecord<T> and override some methods
* Discounts
* Statistics
