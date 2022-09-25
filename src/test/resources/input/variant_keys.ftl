simple-identifier =
    { $sel ->
       *[key] Value
    }

identifier-surrounded-by-whitespace =
    { $sel ->
       *[     key     ] Value
    }

int-number =
    { $sel ->
       *[1] Value
    }

float-number =
    { $sel ->
       *[3.14] Value
    }

# ERROR
invalid-identifier =
    { $sel ->
       *[two words] Value
    }

# ERROR
invalid-int =
    { $sel ->
       *[1 apple] Value
    }

# ERROR
invalid-int =
    { $sel ->
       *[3.14 apples] Value
    }
