package org.artembogomolova.demo.webapp.test.context.event

import org.artembogomolova.demo.webapp.main.dao.repo.IAuthorityRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IRoleRepository
import org.artembogomolova.demo.webapp.main.dao.repo.IUserRepository
import org.springframework.context.ApplicationContext
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.event.ContextStartedEvent
import org.springframework.test.context.TestContext
import org.springframework.test.context.event.EventPublishingTestExecutionListener

open class ContextStartedEventPublishingListener : EventPublishingTestExecutionListener() {
    override fun beforeTestMethod(testContext: TestContext) {
        super.beforeTestExecution(testContext)
        publishStartedEvent(testContext)
    }

    protected fun publishStartedEvent(testContext: TestContext) {
        val applicationContext: ApplicationContext = testContext.applicationContext
        applicationContext.publishEvent(ContextStartedEvent(applicationContext))
    }

    override fun afterTestMethod(testContext: TestContext) {
        val applicationContext: ConfigurableApplicationContext = testContext.applicationContext as ConfigurableApplicationContext
        val userRepository: IUserRepository = applicationContext.beanFactory.getBean(IUserRepository::class.java)
        val userRoleRepository: IRoleRepository = applicationContext.beanFactory.getBean(IRoleRepository::class.java)
        val authorityRepository: IAuthorityRepository = applicationContext.beanFactory.getBean(IAuthorityRepository::class.java)
        userRepository.deleteAll()
        userRoleRepository.deleteAll()
        authorityRepository.deleteAll()
    }
}
class DoubleTimeContextStartedEventPublisherListener : ContextStartedEventPublishingListener() {
    override fun beforeTestMethod(testContext: TestContext) {
        for (i in 0 until RELOAD_TIME_EMULATION_COUNT) {
            publishStartedEvent(testContext)
        }
    }

    companion object {
        private const val RELOAD_TIME_EMULATION_COUNT = 2
    }
}