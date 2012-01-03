Playpal
=======

Description
-----------
Play! framework adaptation of the drupal CMS

How to run
----------

`play build-module custom/cmscore`

`play build-module custom/test-custom #optional`

`play deps --sync`

`play run`

How to access
-------------

Leaves are available undecorated with the following pattern:
`http://localhost:9000/leaf/{uuid}`
Fully decorated pages are available with the following pattern:
`http://localhost:9000/{uuid}`

A couple of examples from the default data is:
Leaf 1 - [http://localhost:9000/leaf/aa1755dd-18c4-4b78-956e-eef7e562c36c]
Leaf 3 - [http://localhost:9000/leaf/1cf699a7-a0c4-4be0-855f-466042a36a8d]

Accessing the same leaves but other versions would be:
Leaf 1 - [http://localhost:9000/leaf/aa1755dd-18c4-4b78-956e-eef7e562c36c/2]
Leaf 3 - [http://localhost:9000/leaf/1cf699a7-a0c4-4be0-855f-466042a36a8d/3]

Accessing the same leaves but as decorated full pages:
Page 1 - [http://localhost:9000/aa1755dd-18c4-4b78-956e-eef7e562c36c]
Page 3 - [http://localhost:9000/1cf699a7-a0c4-4be0-855f-466042a36a8d]

Accessing the same decorated pages but other versions:
Page 1 - [http://localhost:9000/aa1755dd-18c4-4b78-956e-eef7e562c36c/2]
Page 3 - [http://localhost:9000/1cf699a7-a0c4-4be0-855f-466042a36a8d/3]

There is a start page set in the system settings, in the example data it is pointed at Leaf A listed above. It can be accessed at:
[http://localhost:9000/]
There is also a 404 page set in settings, it will be shown any time a page is not found or by accessing it by:
[http://localhost:9000/page-not-found] (An alias, explained below)
or
[http://localhost:9000/c9615819-0556-4e70-b6a9-a66c5b8d4c1a]

There are also aliases, they can have any path, for example:
Page 3 [http://localhost:9000/third]
Page 4 [http://localhost:9000/fourth]
Page 5 [http://localhost:9000/fifth]
