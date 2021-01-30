package org.artembogomolova.demo.webapp.main.validation

import java.util.Locale
import java.util.regex.Pattern
import javax.validation.Constraint
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass
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
            Pair(CountryCode.DE, "\\d{5}"),
            Pair(CountryCode.JP, "\\d{3}-\\d{4}"),
            Pair(CountryCode.FR, "\\d{2}[ ]?\\d{3}"),
            Pair(CountryCode.AU, "\\d{4}"),
            Pair(CountryCode.IT, "\\d{5}"),
            Pair(CountryCode.CH, "\\d{4}"),
            Pair(CountryCode.AT, "\\d{4}"),
            Pair(CountryCode.ES, "\\d{5}"),
            Pair(CountryCode.NL, "\\d{4}[ ]?[A-Z]{2}"),
            Pair(CountryCode.BE, "\\d{4}"),
            Pair(CountryCode.DK, "\\d{4}"),
            Pair(CountryCode.SE, "\\d{3}[ ]?\\d{2}"),
            Pair(CountryCode.NO, "\\d{4}"),
            Pair(CountryCode.BR, "\\d{5}[\\-]?\\d{3}"),
            Pair(CountryCode.PT, "\\d{4}([\\-]\\d{3})?"),
            Pair(CountryCode.FI, "\\d{5}"),
            Pair(CountryCode.AX, "22\\d{3}"),
            Pair(CountryCode.KR, "\\d{3}[\\-]\\d{3}"),
            Pair(CountryCode.CN, "\\d{6}"),
            Pair(CountryCode.TW, "\\d{3}(\\d{2})?"),
            Pair(CountryCode.SG, "\\d{6}"),
            Pair(CountryCode.DZ, "\\d{5}"),
            Pair(CountryCode.AD, "AD\\d{3}"),
            Pair(CountryCode.AR, "([A-HJ-NP-Z])?\\d{4}([A-Z]{3})?"),
            Pair(CountryCode.AM, "(37)?\\d{4}"),
            Pair(CountryCode.AZ, "\\d{4}"),
            Pair(CountryCode.BH, "((1[0-2]|[2-9])\\d{2})?"),
            Pair(CountryCode.BD, "\\d{4}"),
            Pair(CountryCode.BB, "(BB\\d{5})?"),
            Pair(CountryCode.BY, "\\d{6}"),
            Pair(CountryCode.BM, "[A-Z]{2}[ ]?[A-Z0-9]{2}"),
            Pair(CountryCode.BA, "\\d{5}"),
            Pair(CountryCode.IO, "BBND 1ZZ"),
            Pair(CountryCode.BN, "[A-Z]{2}[ ]?\\d{4}"),
            Pair(CountryCode.BG, "\\d{4}"),
            Pair(CountryCode.KH, "\\d{5}"),
            Pair(CountryCode.CV, "\\d{4}"),
            Pair(CountryCode.CL, "\\d{7}"),
            Pair(CountryCode.CR, "\\d{4,5}|\\d{3}-\\d{4}"),
            Pair(CountryCode.HR, "\\d{5}"),
            Pair(CountryCode.CY, "\\d{4}"),
            Pair(CountryCode.CZ, "\\d{3}[ ]?\\d{2}"),
            Pair(CountryCode.DO, "\\d{5}"),
            Pair(CountryCode.EC, "([A-Z]\\d{4}[A-Z]|(?:[A-Z]{2})?\\d{6})?"),
            Pair(CountryCode.EG, "\\d{5}"),
            Pair(CountryCode.EE, "\\d{5}"),
            Pair(CountryCode.FO, "\\d{3}"),
            Pair(CountryCode.GE, "\\d{4}"),
            Pair(CountryCode.GR, "\\d{3}[ ]?\\d{2}"),
            Pair(CountryCode.GL, "39\\d{2}"),
            Pair(CountryCode.GT, "\\d{5}"),
            Pair(CountryCode.HT, "\\d{4}"),
            Pair(CountryCode.HN, "(?:\\d{5})?"),
            Pair(CountryCode.HU, "\\d{4}"),
            Pair(CountryCode.IS, "\\d{3}"),
            Pair(CountryCode.IN, "\\d{6}"),
            Pair(CountryCode.ID, "\\d{5}"),
            Pair(CountryCode.IL, "\\d{5}"),
            Pair(CountryCode.JO, "\\d{5}"),
            Pair(CountryCode.KZ, "\\d{6}"),
            Pair(CountryCode.KE, "\\d{5}"),
            Pair(CountryCode.KW, "\\d{5}"),
            Pair(CountryCode.LA, "\\d{5}"),
            Pair(CountryCode.LV, "\\d{4}"),
            Pair(CountryCode.LB, "(\\d{4}([ ]?\\d{4})?)?"),
            Pair(CountryCode.LI, "(948[5-9])|(949[0-7])"),
            Pair(CountryCode.LT, "\\d{5}"),
            Pair(CountryCode.LU, "\\d{4}"),
            Pair(CountryCode.MK, "\\d{4}"),
            Pair(CountryCode.MY, "\\d{5}"),
            Pair(CountryCode.MV, "\\d{5}"),
            Pair(CountryCode.MT, "[A-Z]{3}[ ]?\\d{2,4}"),
            Pair(CountryCode.MU, "(\\d{3}[A-Z]{2}\\d{3})?"),
            Pair(CountryCode.MX, "\\d{5}"),
            Pair(CountryCode.MD, "\\d{4}"),
            Pair(CountryCode.MC, "980\\d{2}"),
            Pair(CountryCode.MA, "\\d{5}"),
            Pair(CountryCode.NP, "\\d{5}"),
            Pair(CountryCode.NZ, "\\d{4}"),
            Pair(CountryCode.NI, "((\\d{4}-)?\\d{3}-\\d{3}(-\\d{1})?)?"),
            Pair(CountryCode.NG, "(\\d{6})?"),
            Pair(CountryCode.OM, "(PC )?\\d{3}"),
            Pair(CountryCode.PK, "\\d{5}"),
            Pair(CountryCode.PY, "\\d{4}"),
            Pair(CountryCode.PH, "\\d{4}"),
            Pair(CountryCode.PL, "\\d{2}-\\d{3}"),
            Pair(CountryCode.PR, "00[679]\\d{2}([ \\-]\\d{4})?"),
            Pair(CountryCode.RO, "\\d{6}"),
            Pair(CountryCode.RU, "\\d{6}"),
            Pair(CountryCode.SM, "4789\\d"),
            Pair(CountryCode.SA, "\\d{5}"),
            Pair(CountryCode.SN, "\\d{5}"),
            Pair(CountryCode.SK, "\\d{3}[ ]?\\d{2}"),
            Pair(CountryCode.SI, "\\d{4}"),
            Pair(CountryCode.ZA, "\\d{4}"),
            Pair(CountryCode.LK, "\\d{5}"),
            Pair(CountryCode.TJ, "\\d{6}"),
            Pair(CountryCode.TH, "\\d{5}"),
            Pair(CountryCode.TN, "\\d{4}"),
            Pair(CountryCode.TR, "\\d{5}"),
            Pair(CountryCode.TM, "\\d{6}"),
            Pair(CountryCode.UA, "\\d{5}"),
            Pair(CountryCode.UY, "\\d{5}"),
            Pair(CountryCode.UZ, "\\d{6}"),
            Pair(CountryCode.VA, "00120"),
            Pair(CountryCode.VE, "\\d{4}"),
            Pair(CountryCode.ZM, "\\d{5}"),
            Pair(CountryCode.AS, "96799"),
            Pair(CountryCode.CC, "6799"),
            Pair(CountryCode.CK, "\\d{4}"),
            Pair(CountryCode.RS, "\\d{6}"),
            Pair(CountryCode.ME, "8\\d{4}"),
            Pair(CountryCode.CS, "\\d{5}"),
            Pair(CountryCode.YU, "\\d{5}"),
            Pair(CountryCode.CX, "6798"),
            Pair(CountryCode.ET, "\\d{4}"),
            Pair(CountryCode.FK, "FIQQ 1ZZ"),
            Pair(CountryCode.NF, "2899"),
            Pair(CountryCode.FM, "(9694[1-4])([ \\-]\\d{4})?"),
            Pair(CountryCode.GF, "9[78]3\\d{2}"),
            Pair(CountryCode.GN, "\\d{3}"),
            Pair(CountryCode.GP, "9[78][01]\\d{2}"),
            Pair(CountryCode.GS, "SIQQ 1ZZ"),
            Pair(CountryCode.GU, "969[123]\\d([ \\-]\\d{4})?"),
            Pair(CountryCode.GW, "\\d{4}"),
            Pair(CountryCode.HM, "\\d{4}"),
            Pair(CountryCode.IQ, "\\d{5}"),
            Pair(CountryCode.KG, "\\d{6}"),
            Pair(CountryCode.LR, "\\d{4}"),
            Pair(CountryCode.LS, "\\d{3}"),
            Pair(CountryCode.MG, "\\d{3}"),
            Pair(CountryCode.MH, "969[67]\\d([ \\-]\\d{4})?"),
            Pair(CountryCode.MN, "\\d{6}"),
            Pair(CountryCode.MP, "9695[012]([ \\-]\\d{4})?"),
            Pair(CountryCode.MQ, "9[78]2\\d{2}"),
            Pair(CountryCode.NC, "988\\d{2}"),
            Pair(CountryCode.NE, "\\d{4}"),
            Pair(CountryCode.VI, "008(([0-4]\\d)|(5[01]))([ \\-]\\d{4})?"),
            Pair(CountryCode.PF, "987\\d{2}"),
            Pair(CountryCode.PG, "\\d{3}"),
            Pair(CountryCode.PM, "9[78]5\\d{2}"),
            Pair(CountryCode.PN, "PCRN 1ZZ"),
            Pair(CountryCode.PW, "96940"),
            Pair(CountryCode.RE, "9[78]4\\d{2}"),
            Pair(CountryCode.SH, "(ASCN|STHL) 1ZZ"),
            Pair(CountryCode.SJ, "\\d{4}"),
            Pair(CountryCode.SO, "\\d{5}"),
            Pair(CountryCode.SZ, "[HLMS]\\d{3}"),
            Pair(CountryCode.TC, "TKCA 1ZZ"),
            Pair(CountryCode.WF, "986\\d{2}"),
            Pair(CountryCode.XK, "\\d{5}"),
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