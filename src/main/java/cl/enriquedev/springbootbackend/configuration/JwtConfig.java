package cl.enriquedev.springbootbackend.configuration;

public class JwtConfig {

    public static final String LLAVE_SECRETA = "llave.secreta.1234";
    public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEAujbKHz8cl9vNkNDxTVmMTEyFyh6bD/lKrodAUifdJ9UOq4+s\n" +
            "t/AnrIskOfqrv93YI+QsEpMahsuZ92ErOIanKmzhwgL1n/65/O+D9qyEcK110JSm\n" +
            "kQnIiXFodXl5iS7hHFttSk4LTi+vpjAO0Q7fWeBd82S/4MBs6yD0PWV7ecFNIiK7\n" +
            "CmadnGrxg2f05gbf7TctRlcgIvV54A0VHc/sh+q463nc/yOlVN1oIR2rtL2WEeV+\n" +
            "z6cvacjKXHDE/Azl433d5QXwvdHr6cQHJyS1dQSeRNIjJVSAzz/jRJ1Y8Eiuuq7p\n" +
            "ltHlOdeKdh1RQRO+95pvBiGiFZNV9V2PBscQWwIDAQABAoIBAQCWKmzgudJjHBwv\n" +
            "9m9BQz+CoAscLYY6YXy6NdPG7OLJpmUIMPrF8mLsH+D86IrsVQhiGTT5Xat8nF/z\n" +
            "QGbMVLol2lxMSnqtpYtyz+wJVqIPA7MIm3efatZPECd+5JD/8dATQB9oVnuDd60c\n" +
            "RZENQgMXxXm+N7cMleC/1HlhsZ7bVYZqESR1/KP6rs1Rgcgr1sTlbHC53X9P7ajl\n" +
            "kAYMqnOFYaBxN0iEX9P5Lb1hj/j9xqvEXZzmE4jOGD0eoGgN5134appZ/0WfCqeA\n" +
            "qCXOktcXp6LNwEEU/YQlhavlO77393Gx5ajnVO6vRwc/VwJMMSt5ZOsEVooJnIMB\n" +
            "Qq1y56ohAoGBAPR69H/jkvrlNfL/4/xcSaROsNYcUb/mPTxA/LvXf6iqxZNCLrqC\n" +
            "Q7O4dN2GtEODgJoNJ7Mgq4NUG+B6QDbCyS6xpwvwckpqfMjdzRfmPwls7t8VqiW0\n" +
            "EcOa7Nl7asB5dUNIfffPYgXk7RpcidZ6vj6BVC6+OxfAisx81iRXrdwDAoGBAML8\n" +
            "/0qHyVjq1FlG9iImImiePMLNw1aUy+IxEuh+5cQo/rdrd6HOdDuyx2RUf4CO8LH9\n" +
            "MpXod+d7erwQ1rUoTdIR1mtR6SptQXpMYWQ66qmvSvMw9nPIUm//UEoqqJoD/pIf\n" +
            "eGDdPD787EQu77qBQzj5GLTvahRVhlb4LUmpCcbJAoGAGAgGBJQwHG1vciVpGaNQ\n" +
            "sc/yXfmGp6BQIJ4aubQjHkc7CJCW20p2sTHOewAEtCY2++2TG254rX4VB2m86iUX\n" +
            "dXmM/AV0lpAFGBGFWXYOeDS2/kK46N/cZyfRZEdXZPgLaMO1bih6cC3iiJbUDbse\n" +
            "/DHmPnoAgQMyIYub2RKtSlUCgYBZY1o1dKJdXOoUM2/Kn+jORRxdwa53FqeQ/iZI\n" +
            "50O19YUv2NhzGQ43//FgkUwSR3UYY5a5GmU9fWH6rU2ApwVl9D9z240kogmOY02S\n" +
            "gV/qgi295whUc9U2+rh4B65bTNF7c2ngHgvsZE57w3iq629BwxAMgmUDBWIcpJE+\n" +
            "X3vX4QKBgQCdRGxSB5XCk5EAvZ+DPwPs0GKrzkysCSdNyvCIjzjsLnGZZq0HdRCA\n" +
            "LTeVxy519yGdgP8GVSIGQPZpMh0hHa05vrAURaRLOCL+kY2IjJHcMgpyzg++CBtJ\n" +
            "xmYALqoSzr0nREFT378xnHLH1Sz2PqwEa/yVV5IxrIlkpIYqA1FIEQ==\n" +
            "-----END RSA PRIVATE KEY-----";

    public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAujbKHz8cl9vNkNDxTVmM\n" +
            "TEyFyh6bD/lKrodAUifdJ9UOq4+st/AnrIskOfqrv93YI+QsEpMahsuZ92ErOIan\n" +
            "KmzhwgL1n/65/O+D9qyEcK110JSmkQnIiXFodXl5iS7hHFttSk4LTi+vpjAO0Q7f\n" +
            "WeBd82S/4MBs6yD0PWV7ecFNIiK7CmadnGrxg2f05gbf7TctRlcgIvV54A0VHc/s\n" +
            "h+q463nc/yOlVN1oIR2rtL2WEeV+z6cvacjKXHDE/Azl433d5QXwvdHr6cQHJyS1\n" +
            "dQSeRNIjJVSAzz/jRJ1Y8Eiuuq7pltHlOdeKdh1RQRO+95pvBiGiFZNV9V2PBscQ\n" +
            "WwIDAQAB\n" +
            "-----END PUBLIC KEY-----";
}
