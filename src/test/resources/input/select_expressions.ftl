new-messages =
    { BUILTIN() ->
        [0] Zero
       *[other] {""}Other
    }

valid-selector-term-attribute =
    { -term.case ->
       *[key] Value
    }

# ERROR Term values are not valid selectors
invalid-selector-term-value =
    { -term ->
       *[key] Value
    }

# ERROR CallExpressions on Terms are similar to TermReferences
invalid-selector-term-variant =
    { -term(case: "nominative") ->
       *[key] Value
    }

empty-variant =
    { $sel ->
       *[key] {""}
    }

reduced-whitespace =
    {FOO()->
       *[key] {""}
    }

nested-select =
    { $sel ->
       *[one] { $sel ->
          *[two] Value
       }
    }

# ERROR Missing selector
missing-selector =
    {
       *[key] Value
    }

# ERROR Missing line end after variant list
missing-line-end =
    { $sel ->
        *[key] Value}
