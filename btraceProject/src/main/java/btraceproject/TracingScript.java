package btraceproject;

import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;

@BTrace
public class TracingScript {

	@Property
	private static long start;

	@OnMethod(clazz = "writer.MyCsvCustomWriter",
			  method = "printHello",
			  location = @Location(value = Kind.RETURN))
	public static void routineDataAccessObjectCallReturn(@Duration final long duration,
                                                         @ProbeClassName final String className,
            											 @ProbeMethodName final String probeMethod) {
        final String string = strcat(strcat(className, "."), strcat(probeMethod, "() "));
        println(strcat(string, Strings.str(duration)));
        /*
		println(timeNanos() - start);
        print(duration);
        */
	}

	@OnMethod(clazz = "writer.MyCsvCustomWriter",
			  method = "printHello",
			  location = @Location(value = Kind.ENTRY))
	public static void routineDataAccessObjectCallEntry(@ProbeClassName final String className,
            											@ProbeMethodName final String probeMethod) {
		start = timeNanos();
	}

}