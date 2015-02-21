package btraceproject.dpp;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class CashFlowsBTraceScript {

	private static long unmarshallerTime;
	private static long cleanseTime;
	private static long transformTime;
	private static long validateTime;
	private static long validateCfTime;
	private static long saveTime;
	private static long excepTime;

	@OnMethod(clazz = "com.db.treasury.tch.feed.fcl.in.mapping.CashFlowUnmarshaller",
			  method = "getNewInstance",
			  location = @Location(value = Kind.RETURN))
	public static final void cashFlowUnmarshaller_getNewInstanceReturn(@Duration final long duration,
																 	   @ProbeClassName final String className,
																 	   @ProbeMethodName final String probeMethod) {
		unmarshallerTime += duration / 1000000;
	}


	@OnMethod(clazz = "com.db.treasury.tch.dpp.task.CleanseTask",
			  method = "call",
			  location = @Location(value = Kind.RETURN))
	public static final void cleanseTask_callReturn(@Duration final long duration,
													@ProbeClassName final String className,
													@ProbeMethodName final String probeMethod) {
		cleanseTime += duration / 1000000;
	}

	@OnMethod(clazz = "com.db.treasury.tch.feed.fcl.transform.CashFlowToCanonical",
			  method = "transform",
			  location = @Location(value = Kind.RETURN))
	public static final void cashFlowToCanonical_transformReturn(@Duration final long duration,
															 	 @ProbeClassName final String className,
															 	 @ProbeMethodName final String probeMethod) {
		transformTime += duration / 1000000;
	}

	@OnMethod(clazz = "com.db.treasury.tch.dpp.task.ValidateTask",
			  method = "call",
			  location = @Location(value = Kind.RETURN))
	public static final void cashFlowToCanonical_callReturn(@Duration final long duration,
														 	@ProbeClassName final String className,
														 	@ProbeMethodName final String probeMethod) {
		validateTime += duration / 1000000;
	}

	@OnMethod(clazz = "com.db.treasury.tch.core.services.validate.ValidateCashFlowTypeService",
			  method = "validate",
			  location = @Location(value = Kind.RETURN))
	public static final void ValidateCashFlowTypeService_validateReturn(@Duration final long duration,
																	    @ProbeClassName final String className,
																	    @ProbeMethodName final String probeMethod) {
		validateCfTime += duration / 1000000;
	}

	@OnMethod(clazz = "com.db.treasury.tch.core.services.persistence.SimplePersistenceService",
			  method = "save",
			  location = @Location(value = Kind.RETURN))
	public static final void persistenceService_saveReturn(@Duration final long duration,
														   @ProbeClassName final String className,
														   @ProbeMethodName final String probeMethod) {
		saveTime += duration / 1000000;
	}

	@OnMethod(clazz = "com.db.treasury.tch.data.service.ExceptionPersistenceServiceImpl",
			  method = "saveExceptions",
			  location = @Location(value = Kind.RETURN))
	public static final void exceptionPersistenceService_saveReturn(@Duration final long duration,
																    @ProbeClassName final String className,
																    @ProbeMethodName final String probeMethod) {
		excepTime += duration / 1000000;
	}


	@OnMethod(clazz = "com.db.treasury.tch.dpp.task.ExceptionAwarePersisterTask",
			  method = "onNext",
			  location = @Location(value = Kind.RETURN))
	public static final void exceptionAwarePersisterTask_onNextReturn(@Duration final long duration,
														   @ProbeClassName final String className,
														   @ProbeMethodName final String probeMethod) {
		println(strcat("com.db.treasury.tch.feed.fcl.in.mapping.CashFlowUnmarshaller.getNewInstance() unmarshaller ", Strings.str(unmarshallerTime)));
		println(strcat("com.db.treasury.tch.dpp.task.CleanseTask.call() cleanse ", Strings.str(cleanseTime)));
		println(strcat("com.db.treasury.tch.feed.fcl.transform.CashFlowToCanonical.transform() transform ", Strings.str(transformTime)));
		println(strcat("com.db.treasury.tch.dpp.task.ValidateTask.call() validate ", Strings.str(validateTime)));
		println(strcat("com.db.treasury.tch.core.services.validate.ValidateCashFlowTypeService.validate() validateCashFlowType ", Strings.str(validateCfTime)));
		println(strcat("com.db.treasury.tch.core.services.persistence.SimplePersistenceService.save() save ", Strings.str(saveTime)));
		println(strcat("com.db.treasury.tch.data.service.ExceptionPersistenceServiceImpl.saveExceptions() saveExceptions ", Strings.str(excepTime)));
		println(strcat(strcat(strcat(className, "."), strcat(probeMethod, "() ")), Strings.str(duration / 1000000)));
		unmarshallerTime = 0;
		cleanseTime = 0;
		transformTime = 0;
		validateTime = 0;
		validateCfTime = 0;
		saveTime = 0;
		excepTime = 0;
	}

}