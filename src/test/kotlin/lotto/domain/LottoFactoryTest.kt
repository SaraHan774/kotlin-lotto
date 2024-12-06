package lotto.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import lotto.domain.data.LottoNumber
import lotto.util.NumberGenerator

class LottoFactoryTest : StringSpec({
    "{given} LottoFactory(totalLottoCount=5) {when} createLottoList() {then} 로또 총 5개 " {
        // Given
        val totalLottoCount = 5
        val expectedLottoNumbers = listOf(1, 2, 3, 4, 5, 6)
        val fakeNumberGenerator = FakeRandomNumberGenerator(expectedLottoNumbers)
        val lottoFactory =
            LottoFactory(
                totalLottoCount = totalLottoCount,
                numberGenerator = fakeNumberGenerator,
            )

        // When
        val lottoList = lottoFactory.createLottoList()

        // Then
        lottoList shouldHaveSize totalLottoCount
        lottoList.forEach { lotto ->
            lotto.value shouldBe expectedLottoNumbers.map { LottoNumber(it) }
        }
    }

    "{given} LottoFactory(fakeNumberGenerator) {when} createLottoList() {then} 로또 번호 fake 번호들과 일치" {
        // Given
        val expectedLottoNumbers = listOf(1, 2, 3, 4, 5, 6)
        val fakeNumberGenerator = FakeRandomNumberGenerator(expectedLottoNumbers)
        val lottoFactory =
            LottoFactory(
                totalLottoCount = 5,
                numberGenerator = fakeNumberGenerator,
            )

        // When
        val lottoList = lottoFactory.createLottoList()

        // Then
        lottoList.forEach { lotto -> lotto.value shouldBe expectedLottoNumbers.map { LottoNumber(it) } }
    }
})

class FakeRandomNumberGenerator(private val numbers: List<Int>) : NumberGenerator {
    override fun getNumbers(count: Int): List<Int> = numbers
}
