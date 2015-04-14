package cool.arch.stateroom;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

abstract class AbstractBuilderImpl<T> implements AbstractBuilder<T> {

	final T instance;

	protected AbstractBuilderImpl(final T instance) {
		this.instance = requireNonNull(instance, "instance shall not be null");
	}

	@Override
	public final T build() {
		final Set<String> errors = validate(new HashSet<>(), getInstance());

		if (!errors.isEmpty()) {
			final Class<?> instanceClass = instance.getClass();
			final StringJoiner joiner = new StringJoiner(", ", "Instance of " + instanceClass.getName()
				+ " is not valid to be built: ", ".");
			errors.forEach(joiner::add);

			throw new IllegalStateException(joiner.toString());
		}

		return instance;
	}

	protected T getInstance() {
		return instance;
	}

	protected abstract Set<String> validate(Set<String> errors, T instance);
}