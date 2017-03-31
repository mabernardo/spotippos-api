[![Build Status](https://travis-ci.org/mabernardo/spotippos-api.svg?branch=master)](https://travis-ci.org/mabernardo/spotippos-api)

# spotippos-api
Spotippos REST API

## Para iniciar o projeto

    ./gradlew bootRun

## Uso

### Criar um imóvel
Request:

    POST /properties
    Content-Type: application/json

Body:

    {
        "x": 222,
        "y": 444,
        "title": "Imóvel código 1, com 5 quartos e 4 banheiros",
        "price": 1250000,
        "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
        "beds": 4,
        "baths": 3,
        "squareMeters": 210
    }

Response Headers:

    Status: 201 Created
    Location: {URI}/properties/8001

Response Body:

    {
        "id": 8001,
        "title": "Imóvel com 3 quartos e 2 banheiros.",
        "price": 700000,
        "description": "Descrição do Imóvel.",
        "beds": 3,
        "baths": 2,
        "squareMeters": 80,
        "provinces": [ "Nova" ],
        "lat": 1257,
        "long": 228
    }

Exemplo:

    curl --request POST \
        --url http://localhost:8080/properties \
        --header 'content-type: application/json' \
        --data '{ "title": "Imóvel com 3 quartos e 2 banheiros.", "price": 700000, "description": "Descrição do Imóvel.", "lat": 1257, "long": 228, "beds": 3, "baths": 2, "squareMeters": 80 }'

### Buscar um imóvel específico

Request:

    GET /properties/{id}

Caso exista propriedade com o id informado:

Response Headers:

    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

Response Body:

    {
        "id": 666,
        "title": "Imóvel código 666, com 4 quartos e 3 banheiros.",
        "price": 869000,
        "description": "Aliqua deserunt qui in nulla do est sunt ullamco fugiat. Velit ad eu eu aliqua minim elit ut laborum irure irure anim officia aliqua.",
        "beds": 4,
        "baths": 3,
        "squareMeters": 84,
        "provinces": [ "Nova" ],
        "lat": 1125,
        "long": 72
    }

Caso não seja encontrada:

Response Headers:

    Status: 404
    Content-Type: application/json;charset=UTF-8

Response Body:

    {
        "timestamp": 1490942171445,
        "status": 404,
        "error": "Not Found",
        "exception": "spotippos.rest.exception.PropertyNotFoundException",
        "message": "Propriedade não encontrada '9999'.",
        "path": "/properties/9999"
    }

Exemplo:

    curl --request GET --url http://localhost:8080/properties/666


### Buscar imóveis em uma área

Request:

    GET /properties?ax={integer}&ay={integer}&bx={integer}&by={integer}

Response Headers:

    Status: 200 OK
    Content-Type: application/json;charset=UTF-8

Response Body:

    {
        "foundProperties": 18,
        "properties": [
            {
                "id": 247,
                "title": "Imóvel código 247, com 5 quartos e 4 banheiros.",
                "price": 1768000,
                "description": "Do in incididunt ...",
                "beds": 5,
                "baths": 4,
                "squareMeters": 172,
                "provinces": [  "Scavy" ],
                "lat": 84,
                "long": 74
            },
            { ... },
            { ... },
            { ... }
        ]
    }

Exemplo:

    curl --request GET --url 'http://localhost:8080/properties?ax=50&ay=100&bx=100&by=50'

## Dependencies
JRE 1.8+