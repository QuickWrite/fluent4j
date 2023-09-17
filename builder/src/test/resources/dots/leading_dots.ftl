key01 = .Value
key02 = …Value
key03 = {"."}Value
key04 =
    {"."}Value

key05 = Value
    {"."}Continued

key06 = .Value
    {"."}Continued

key10 =
    .Value = which is an attribute
    Continued

key10-attribute = { key10.Value }

key11 =
    {"."}Value = which looks like an attribute
    Continued

key12 =
    .accesskey =
    A

key12-attribute = { key12.accesskey }

key13 =
    .attribute = .Value

key13-attribute = { key13.attribute }

key14 =
    .attribute =
         {"."}Value

key14-attribute = { key14.attribute }

key15 =
    { 1 ->
        [one] .Value
       *[other]
            {"."}Value
    }

key16 =
    { 1 ->
       *[one]
           .Value
    }

key17 =
    { 1 ->
       *[one] Value
           .Continued
    }