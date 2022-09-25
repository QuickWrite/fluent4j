## Reference expressions in placeables.

message-reference-placeable = {msg}
term-reference-placeable = {-term}
variable-reference-placeable = {$var}

## Reference expressions in selectors.

variable-reference-selector = {$var ->
   *[key] Value
}

# ERROR Message values may not be used as selectors.
message-reference-selector = {msg ->
   *[key] Value
}
# ERROR Term values may not be used as selectors.
term-reference-selector = {-term ->
   *[key] Value
}

## Check if valid references work
valid-message-reference-placeable = {valid-msg}
valid-term-reference-placeable = {-valid-term}
valid-variable-reference-placeable = {$var}

valid-msg = Valid Message
-valid-term = Valid Term
