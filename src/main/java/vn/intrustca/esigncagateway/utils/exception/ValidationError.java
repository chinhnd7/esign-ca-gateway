package vn.intrustca.esigncagateway.utils.exception;

public @interface ValidationError {
    String AssertFalse = "validation.constraints.AssertFalse";
    String AssertTrue = "validation.constraints.AssertTrue";
    String DecimalMax = "validation.constraints.DecimalMax";
    String DecimalMin = "validation.constraints.DecimalMin";
    String Digits = "validation.constraints.Digits";
    String Email = "validation.constraints.Email";
    String Future = "validation.constraints.Future";
    String FutureOrPresent = "validation.constraints.FutureOrPresent";
    String Max = "validation.constraints.Max";
    String Min = "validation.constraints.Min";
    String Negative = "validation.constraints.Negative";
    String NegativeOrZero = "validation.constraints.NegativeOrZero";
    String NotBlank = "validation.constraints.NotBlank";
    String NotEmpty = "validation.constraints.NotEmpty";
    String NotNull = "validation.constraints.NotNull";
    String Null = "validation.constraints.Null";
    String Past = "validation.constraints.Past";
    String PastOrPresent = "validation.constraints.PastOrPresent";
    String Pattern = "validation.constraints.Pattern";
    String Positive = "validation.constraints.Positive";
    String PositiveOrZero = "validation.constraints.PositiveOrZero";
    String Size = "validation.constraints.Size";
    String Duplicate = "validation.constraints.Duplicate";
    String NotFound = "validation.constraints.NotFound";
    String Invalid = "validation.constraints.Invalid";
    String InUse = "validation.constraints.InUse";

    String ServerError = "validation.constraints.ServerError";
    String PublicKey = "validation.constraints.PublicKey";

    String s3upload = "validation.constraints.s3Upload";
    String authentication = "validation.constraints.authentication";
    String raAuthentication = "validation.fail.authentication";

}