## Tests if the program crashes if something
## is recursive
recursive-message1 = { recursive-message1 }

recursive-term1 = { -recursive-term1 }
-recursive-term1 = { -recursive-term1 }

recursive-selector = { -recursive-selector }
-recursive-selector = { -recursive-selector.test ->
    *[test] Test
 }
  .test = { -recursive-selector }
