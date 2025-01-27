# Data-Privacy-Vault
Java implementation of a data privacy vault - following the https://codingchallenges.substack.com/p/coding-challenge-48-data-privacy tutorial

### Examples
#### REST POST

****
Endpoint: /tokenize

**Request**
```
{
	"id": req-12345”,
	"data": {
		"field1": "value1",
		"field2": "value2",
		"fieldn": "valuen"
	}
}
```
**Response**
```
{
	"id": req-12345”,
	"data": {
		"field1": "t6yh4f6",
		"field2": "gh67ned",
		"fieldn": "bnj7ytb"
	}
}
```

****

Endpoint: /detokenize

**Request**
```
{
	"id": req-33445”,
	"data": {
		"field1": "t6yh4f6",
		"field2": "gh67ned",
		"field3": "invalid token"
	}
}
```

**Response**
```
	"id": req-33445”,
	"data": {
		"field1": {
			"found": true,
			"value": "value1"
		},
		"field2": {
			"found": true,
			"value": "value2"
		},
		"fieldn": {
			"found": false,
			"value": ""
		}
	}
```