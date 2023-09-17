## Tests if the program crashes if something
## is recursive
recursive-message1 = { recursive-message1 }

recursive-term1 = { -recursive-term }

recursive-term2 = This is a recursive {-recursive-term}.

-recursive-term = { -recursive-term }

recursive-selector = { -recursive-selector }
-recursive-selector = { -recursive-selector.test ->
    *[test] Test
 }
  .test = { -recursive-selector }

-non-recursive = Value

non-recursion = {-non-recursive}
double-non-recursion = {-non-recursive} {-non-recursive}
