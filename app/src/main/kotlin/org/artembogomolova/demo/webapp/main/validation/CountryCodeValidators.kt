package org.artembogomolova.demo.webapp.main.validation

import java.util.Locale
import java.util.regex.Pattern
import javax.validation.Constraint
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass
import org.artembogomolova.demo.webapp.main.domain.core.ConstraintPatterns.D3_D2_PATTERN
import org.artembogomolova.demo.webapp.main.domain.core.ConstraintPatterns.D3_PATTERN
import org.artembogomolova.demo.webapp.main.domain.core.ConstraintPatterns.D4_PATTERN
import org.artembogomolova.demo.webapp.main.domain.core.ConstraintPatterns.D5_PATTERN
import org.artembogomolova.demo.webapp.main.domain.core.ConstraintPatterns.D6_PATTERN
import org.artembogomolova.demo.webapp.main.domain.core.CountryCode
import org.artembogomolova.demo.webapp.main.domain.core.PhysicalAddress

@MustBeDocumented
@Constraint(validatedBy = [CountryCodeAndZipCodeValidator::class])
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class ValidCountryCodeAndZipCode(
    val message: String = VIOLATION_MESSAGE_TEMPLATE,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
) {
    companion object {
        const val VIOLATION_MESSAGE_TEMPLATE: String = "org.artembogomolova.demo.webapp.main.validation.zipcode"
    }
}

class CountryCodeAndZipCodeValidator : AbstractApplicationContextConstraintValidator<ValidCountryCodeAndZipCode, PhysicalAddress>() {
    companion object {

        val countryZipCodeMap: Map<CountryCode, String> = mapOf(
            Pair(
                CountryCode.GB,
                "GIR[ ]?0AA|((AB|AL|B|BA|BB|BD|BH|BL|BN|BR|BS|BT|CA|CB|CF|CH|CM|CO|CR|CT|CV|CW|DA|DD|DE|DG|DH|DL|DN|DT|DY|E|EC|EH|EN|EX|FK|FY|G|GL|GY|GU|HA|" +
                        "HD|HG|HP|HR|HS|HU|HX|IG|IM|IP|IV|JE|KA|KT|KW|KY|L|LA|LD|LE|LL|LN|LS|LU|M|ME|MK|ML|N|NE|NG|NN|NP|NR|NW|OL|OX|PA|PE|PH|PL|PO|PR|RG|" +
                        "RH|RM|S|SA|SE|SG|SK|SL|SM|SN|SO|SP|SR|SS|ST|SW|SY|TA|TD|TF|TN|TQ|TR|TS|TW|UB|W|WA|WC|WD|WF|WN|WR|WS|WV|YO|ZE)" +
                        "(\\d[\\dA-Z]?[ ]?\\d[ABD-HJLN-UW-Z]{2}))|BFPO[ ]?\\d{1,4}"
            ),
            Pair(CountryCode.JE, "JE\\d[\\dA-Z]?[ ]?\\d[ABD-HJLN-UW-Z]{2}"),
            Pair(CountryCode.GG, "GY\\d[\\dA-Z]?[ ]?\\d[ABD-HJLN-UW-Z]{2}"),
            Pair(CountryCode.IM, "IM\\d[\\dA-Z]?[ ]?\\d[ABD-HJLN-UW-Z]{2}"),
            Pair(CountryCode.US, "\\d{5}([ \\-]\\d{4})?"),
            Pair(CountryCode.CA, "[ABCEGHJKLMNPRSTVXY]\\d[ABCEGHJ-NPRSTV-Z][ ]?\\d[ABCEGHJ-NPRSTV-Z]\\d"),
            Pair(CountryCode.DE, D5_PATTERN),
            Pair(CountryCode.JP, "\\d{3}-\\d{4}"),
            Pair(CountryCode.FR, "\\d{2}[ ]?\\d{3}"),
            Pair(CountryCode.AU, D4_PATTERN),
            Pair(CountryCode.IT, D5_PATTERN),
            Pair(CountryCode.CH, D4_PATTERN),
            Pair(CountryCode.AT, D4_PATTERN),
            Pair(CountryCode.ES, D5_PATTERN),
            Pair(CountryCode.NL, "\\d{4}[ ]?[A-Z]{2}"),
            Pair(CountryCode.BE, D4_PATTERN),
            Pair(CountryCode.DK, D4_PATTERN),
            Pair(CountryCode.SE, D3_D2_PATTERN),
            Pair(CountryCode.NO, D4_PATTERN),
            Pair(CountryCode.BR, "\\d{5}[\\-]?\\d{3}"),
            Pair(CountryCode.PT, "\\d{4}([\\-]\\d{3})?"),
            Pair(CountryCode.FI, D5_PATTERN),
            Pair(CountryCode.AX, "22\\d{3}"),
            Pair(CountryCode.KR, "\\d{3}[\\-]\\d{3}"),
            Pair(CountryCode.CN, D6_PATTERN),
            Pair(CountryCode.TW, "\\d{3}(\\d{2})?"),
            Pair(CountryCode.SG, D6_PATTERN),
            Pair(CountryCode.DZ, D5_PATTERN),
            Pair(CountryCode.AD, "AD\\d{3}"),
            Pair(CountryCode.AR, "([A-HJ-NP-Z])?\\d{4}([A-Z]{3})?"),
            Pair(CountryCode.AM, "(37)?\\d{4}"),
            Pair(CountryCode.AZ, D4_PATTERN),
            Pair(CountryCode.BH, "((1[0-2]|[2-9])\\d{2})?"),
            Pair(CountryCode.BD, D4_PATTERN),
            Pair(CountryCode.BB, "(BB\\d{5})?"),
            Pair(CountryCode.BY, D6_PATTERN),
            Pair(CountryCode.BM, "[A-Z]{2}[ ]?[A-Z0-9]{2}"),
            Pair(CountryCode.BA, D5_PATTERN),
            Pair(CountryCode.IO, "BBND 1ZZ"),
            Pair(CountryCode.BN, "[A-Z]{2}[ ]?\\d{4}"),
            Pair(CountryCode.BG, D4_PATTERN),
            Pair(CountryCode.KH, D5_PATTERN),
            Pair(CountryCode.CV, D4_PATTERN),
            Pair(CountryCode.CL, "\\d{7}"),
            Pair(CountryCode.CR, "\\d{4,5}|\\d{3}-\\d{4}"),
            Pair(CountryCode.HR, D5_PATTERN),
            Pair(CountryCode.CY, D4_PATTERN),
            Pair(CountryCode.CZ, D3_D2_PATTERN),
            Pair(CountryCode.DO, D5_PATTERN),
            Pair(CountryCode.EC, "([A-Z]\\d{4}[A-Z]|(?:[A-Z]{2})?\\d{6})?"),
            Pair(CountryCode.EG, D5_PATTERN),
            Pair(CountryCode.EE, D5_PATTERN),
            Pair(CountryCode.FO, D3_PATTERN),
            Pair(CountryCode.GE, D4_PATTERN),
            Pair(CountryCode.GR, D3_D2_PATTERN),
            Pair(CountryCode.GL, "39\\d{2}"),
            Pair(CountryCode.GT, D5_PATTERN),
            Pair(CountryCode.HT, D4_PATTERN),
            Pair(CountryCode.HN, "(?:\\d{5})?"),
            Pair(CountryCode.HU, D4_PATTERN),
            Pair(CountryCode.IS, D3_PATTERN),
            Pair(CountryCode.IN, D6_PATTERN),
            Pair(CountryCode.ID, D5_PATTERN),
            Pair(CountryCode.IL, D5_PATTERN),
            Pair(CountryCode.JO, D5_PATTERN),
            Pair(CountryCode.KZ, D6_PATTERN),
            Pair(CountryCode.KE, D5_PATTERN),
            Pair(CountryCode.KW, D5_PATTERN),
            Pair(CountryCode.LA, D5_PATTERN),
            Pair(CountryCode.LV, D4_PATTERN),
            Pair(CountryCode.LB, "(\\d{4}([ ]?\\d{4})?)?"),
            Pair(CountryCode.LI, "(948[5-9])|(949[0-7])"),
            Pair(CountryCode.LT, D5_PATTERN),
            Pair(CountryCode.LU, D4_PATTERN),
            Pair(CountryCode.MK, D4_PATTERN),
            Pair(CountryCode.MY, D5_PATTERN),
            Pair(CountryCode.MV, D5_PATTERN),
            Pair(CountryCode.MT, "[A-Z]{3}[ ]?\\d{2,4}"),
            Pair(CountryCode.MU, "(\\d{3}[A-Z]{2}\\d{3})?"),
            Pair(CountryCode.MX, D5_PATTERN),
            Pair(CountryCode.MD, D4_PATTERN),
            Pair(CountryCode.MC, "980\\d{2}"),
            Pair(CountryCode.MA, D5_PATTERN),
            Pair(CountryCode.NP, D5_PATTERN),
            Pair(CountryCode.NZ, D4_PATTERN),
            Pair(CountryCode.NI, "((\\d{4}-)?\\d{3}-\\d{3}(-\\d{1})?)?"),
            Pair(CountryCode.NG, "(\\d{6})?"),
            Pair(CountryCode.OM, "(PC )?\\d{3}"),
            Pair(CountryCode.PK, D5_PATTERN),
            Pair(CountryCode.PY, D4_PATTERN),
            Pair(CountryCode.PH, D4_PATTERN),
            Pair(CountryCode.PL, "\\d{2}-\\d{3}"),
            Pair(CountryCode.PR, "00[679]\\d{2}([ \\-]\\d{4})?"),
            Pair(CountryCode.RO, D6_PATTERN),
            Pair(CountryCode.RU, D6_PATTERN),
            Pair(CountryCode.SM, "4789\\d"),
            Pair(CountryCode.SA, D5_PATTERN),
            Pair(CountryCode.SN, D5_PATTERN),
            Pair(CountryCode.SK, D3_D2_PATTERN),
            Pair(CountryCode.SI, D4_PATTERN),
            Pair(CountryCode.ZA, D4_PATTERN),
            Pair(CountryCode.LK, D5_PATTERN),
            Pair(CountryCode.TJ, D6_PATTERN),
            Pair(CountryCode.TH, D5_PATTERN),
            Pair(CountryCode.TN, D4_PATTERN),
            Pair(CountryCode.TR, D5_PATTERN),
            Pair(CountryCode.TM, D6_PATTERN),
            Pair(CountryCode.UA, D5_PATTERN),
            Pair(CountryCode.UY, D5_PATTERN),
            Pair(CountryCode.UZ, D6_PATTERN),
            Pair(CountryCode.VA, "00120"),
            Pair(CountryCode.VE, D4_PATTERN),
            Pair(CountryCode.ZM, D5_PATTERN),
            Pair(CountryCode.AS, "96799"),
            Pair(CountryCode.CC, "6799"),
            Pair(CountryCode.CK, D4_PATTERN),
            Pair(CountryCode.RS, D6_PATTERN),
            Pair(CountryCode.ME, "8\\d{4}"),
            Pair(CountryCode.CS, D5_PATTERN),
            Pair(CountryCode.YU, D5_PATTERN),
            Pair(CountryCode.CX, "6798"),
            Pair(CountryCode.ET, D4_PATTERN),
            Pair(CountryCode.FK, "FIQQ 1ZZ"),
            Pair(CountryCode.NF, "2899"),
            Pair(CountryCode.FM, "(9694[1-4])([ \\-]\\d{4})?"),
            Pair(CountryCode.GF, "9[78]3\\d{2}"),
            Pair(CountryCode.GN, D3_PATTERN),
            Pair(CountryCode.GP, "9[78][01]\\d{2}"),
            Pair(CountryCode.GS, "SIQQ 1ZZ"),
            Pair(CountryCode.GU, "969[123]\\d([ \\-]\\d{4})?"),
            Pair(CountryCode.GW, D4_PATTERN),
            Pair(CountryCode.HM, D4_PATTERN),
            Pair(CountryCode.IQ, D5_PATTERN),
            Pair(CountryCode.KG, D6_PATTERN),
            Pair(CountryCode.LR, D4_PATTERN),
            Pair(CountryCode.LS, D3_PATTERN),
            Pair(CountryCode.MG, D3_PATTERN),
            Pair(CountryCode.MH, "969[67]\\d([ \\-]\\d{4})?"),
            Pair(CountryCode.MN, D6_PATTERN),
            Pair(CountryCode.MP, "9695[012]([ \\-]\\d{4})?"),
            Pair(CountryCode.MQ, "9[78]2\\d{2}"),
            Pair(CountryCode.NC, "988\\d{2}"),
            Pair(CountryCode.NE, D4_PATTERN),
            Pair(CountryCode.VI, "008(([0-4]\\d)|(5[01]))([ \\-]\\d{4})?"),
            Pair(CountryCode.PF, "987\\d{2}"),
            Pair(CountryCode.PG, D3_PATTERN),
            Pair(CountryCode.PM, "9[78]5\\d{2}"),
            Pair(CountryCode.PN, "PCRN 1ZZ"),
            Pair(CountryCode.PW, "96940"),
            Pair(CountryCode.RE, "9[78]4\\d{2}"),
            Pair(CountryCode.SH, "(ASCN|STHL) 1ZZ"),
            Pair(CountryCode.SJ, D4_PATTERN),
            Pair(CountryCode.SO, D5_PATTERN),
            Pair(CountryCode.SZ, "[HLMS]\\d{3}"),
            Pair(CountryCode.TC, "TKCA 1ZZ"),
            Pair(CountryCode.WF, "986\\d{2}"),
            Pair(CountryCode.XK, D5_PATTERN),
            Pair(CountryCode.YT, "976\\d{2}")
        )
    }

    override fun isValid(value: PhysicalAddress, context: ConstraintValidatorContext): Boolean {
        if (isAvailableCheck(value).not()) {
            return false
        }
        return checkCountryPostalCode(value, context)
    }

    private fun checkCountryPostalCode(value: PhysicalAddress, context: ConstraintValidatorContext): Boolean {
        val postalCode = value.postalCode
        val countryCode = value.countryCode
        val postalCodePattern = countryZipCodeMap[countryCode!!]
        val result = Pattern.compile(postalCodePattern!!).matcher(postalCode!!).matches()
        if (result.not()) {
            setErrorMessage(context, value, countryCode, postalCodePattern)
        }
        return result
    }

    private fun setErrorMessage(context: ConstraintValidatorContext, value: PhysicalAddress, countryCode: CountryCode, postalCodePattern: String) {
        val message: String = messageLocalSource.getMessage(
            ValidCountryCodeAndZipCode.VIOLATION_MESSAGE_TEMPLATE,
            arrayOf(value.toString(), countryCode.name, postalCodePattern),
            Locale.getDefault()
        )
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation()
    }

    private fun isAvailableCheck(value: PhysicalAddress): Boolean = (value.postalCode != null) or (value.countryCode != null)


}